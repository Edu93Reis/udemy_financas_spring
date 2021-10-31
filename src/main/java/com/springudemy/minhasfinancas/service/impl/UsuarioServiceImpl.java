package com.springudemy.minhasfinancas.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springudemy.minhasfinancas.exceptions.ErroAutenticacaoException;
import com.springudemy.minhasfinancas.exceptions.RegraNegocioException;
import com.springudemy.minhasfinancas.model.entity.Usuario;
import com.springudemy.minhasfinancas.model.repository.UsuarioRepository;
import com.springudemy.minhasfinancas.service.UsuarioService;

//permite que o Spring gerencie a classe atraves de containeres
@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired //cria instancia do repository pode ser usada aqui ou em cima do nome da classe
	private UsuarioRepository usuarioRepository;
	
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}
	
	//funciona como o @Autorwired
//	public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
//		this.usuarioRepository = usuarioRepository;
//	}

	@Override
	public Usuario autenticar(String email, String senha) {
		
		Optional<Usuario> user = usuarioRepository.findByEmail(email);
		
		if(!user.isPresent()) {
			throw new ErroAutenticacaoException("Usuário não cadastrado!");
		}
		
		if(!user.get().getSenha().equals(senha)) {
			throw new ErroAutenticacaoException("Senha inválida!");
		}
		
		return user.get();
		
	}

	@Override
	@Transactional //cria transacao no bd e commita após salvar
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return usuarioRepository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = usuarioRepository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Usuário já cadastrado para este e-mail!");
		}
	}

	@Override
	public Optional<Usuario> obterPorId(Long id) {
		return usuarioRepository.findById(id);
	}
	
}
