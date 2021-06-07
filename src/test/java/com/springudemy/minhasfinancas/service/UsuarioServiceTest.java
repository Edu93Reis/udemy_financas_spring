package com.springudemy.minhasfinancas.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.springudemy.minhasfinancas.exceptions.RegraNegocioException;
import com.springudemy.minhasfinancas.model.repository.UsuarioRepository;
import com.springudemy.minhasfinancas.service.impl.UsuarioServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	@Autowired
	UsuarioService usuarioService;
	
//	@Autowired
	@MockBean
	UsuarioRepository usuarioRepository;
	
	//executa antes de qualquer teste 
	@Before
	public void setUp() {
		//retorna instancia de UsuarioRepository para teste
//		usuarioRepository = Mockito.mock(UsuarioRepository.class);
		usuarioService = new UsuarioServiceImpl(usuarioRepository);
	}
	
	//indica que nao deve lancar excecao
	@Test(expected = Test.None.class)
	public void testeMetodoValidaEmail() {
		//cenario
		Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
//		usuarioRepository.deleteAll();
		//acao
		usuarioService.validarEmail("email@email.com");
	}

	@Test(expected = RegraNegocioException.class)
	public void erroSeEmailCadastrado() {
		//cenario
//		Usuario usuario  = new Usuario("teste", "email@email.com");
//		usuarioRepository.save(usuario);
		Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
		//acao
		usuarioService.validarEmail("email@email.com");
		
	}
	
}
