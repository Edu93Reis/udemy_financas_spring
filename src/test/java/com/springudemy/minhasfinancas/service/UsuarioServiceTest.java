package com.springudemy.minhasfinancas.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.springudemy.minhasfinancas.exceptions.ErroAutenticacaoException;
import com.springudemy.minhasfinancas.exceptions.RegraNegocioException;
import com.springudemy.minhasfinancas.model.entity.Usuario;
import com.springudemy.minhasfinancas.model.repository.UsuarioRepository;
import com.springudemy.minhasfinancas.service.impl.UsuarioServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
//	@Autowired
	@SpyBean
	UsuarioServiceImpl usuarioService;
	
//	@Autowired
	@MockBean
	UsuarioRepository usuarioRepository;
	
	//executa antes de qualquer teste 
//	@Before
//	public void setUp() {
//		//retorna instancia de UsuarioRepository para teste
////		usuarioRepository = Mockito.mock(UsuarioRepository.class);
//		usuarioService = Mockito.spy(UsuarioServiceImpl.class);
//		
////		usuarioService = new UsuarioServiceImpl(usuarioRepository);
//	}
	
	@Test
	public void deveSalvarUmUsuario() {
		//cenario
		Mockito.doNothing().when(usuarioService).validarEmail(Mockito.anyString());
		Usuario usuario = new Usuario("Joh Doe","email@email.com","senha");		
		Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		
		//acao
		Usuario usuarioSalvo = usuarioService.salvarUsuario(new Usuario());
		
		//verificacao
		Assertions.assertThat(usuarioSalvo).isNotNull();
//		Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1);
		Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("Joh Doe");
		Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
		
	}
	
	@Test(expected = RegraNegocioException.class)
	public void naoDeveSalvarUsuarioComEmailJaSalvo() {
		//cenario
		String email = "email@email.com";
		
		Usuario usuario = new Usuario("Joh Doe",email,"senha");	
		Mockito.doThrow(RegraNegocioException.class).when(usuarioService).validarEmail(email);
		
		//acao
		usuarioService.salvarUsuario(usuario);
		
		//verificacao
		//verifica se nunca chamou o metodo salvar para usuario
		Mockito.verify( usuarioRepository, Mockito.never() ).save(usuario);
		
	}
	
	@Test(expected = Test.None.class)
	public void deveAutenticarUsuarioComSucesso() {
		
		String email = "email@email.com";
		String senha = "senha";
		
		Usuario usuario = new Usuario("Joh Doe","email@email.com","senha");
		
		Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		//acao
		Usuario result = usuarioService.autenticar(email, senha);
		
		Assertions.assertThat(result).isNotNull();
		
	}
	
	@Test(expected = ErroAutenticacaoException.class)
	public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
		
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		usuarioService.autenticar("email@email.com", "senha");
		
	}
	
	@Test
	public void deveLancarErroQuandoSenhaNaoBater() {

		String senha = "senha";
		Usuario usuario = new Usuario("Joh Doe","email@email.com",senha);
		
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		
		Throwable exception = Assertions.catchThrowable( () -> usuarioService.autenticar("email@email.com", "1243") );
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacaoException.class).hasMessage("Senha inválida!");
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
