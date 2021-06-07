package com.springudemy.minhasfinancas.service;

import com.springudemy.minhasfinancas.model.entity.Usuario;

//define os metodos entidade/usaurio
public interface UsuarioService {

	Usuario autenticar(String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	void validarEmail(String email);
	
}
