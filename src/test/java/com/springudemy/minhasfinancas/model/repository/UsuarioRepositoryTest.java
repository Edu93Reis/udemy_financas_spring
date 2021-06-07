package com.springudemy.minhasfinancas.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.springudemy.minhasfinancas.model.entity.Usuario;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")//busca application-"test"//corrigir config
@DataJpaTest//usa commmit e rollback
@AutoConfigureTestDatabase(replace = Replace.NONE)
//@ExtendWith( SpringExtension.class )//versao mais recente
public class UsuarioRepositoryTest {

	@Autowired
	public UsuarioRepository usuarioRepository;
	
	@Autowired
	TestEntityManager entityManager;
	
	//testes sempre serao void
	@Test
	public void verificaExistenciaEmailTrue() {
		//3 elementos de teste: 
		//cenario
		Usuario usuario = criarUsuario();
//		builder().nome("usuario").email("usuario@email.com").build();//Lombok
//		usuarioRepository.save(usuario);
		entityManager.persist(usuario);
		
		//acao/execucao
		boolean result = usuarioRepository.existsByEmail("usuario@email.com");
		
		//verificacao
		//Assertions - classe de testes do spring
		Assertions.assertThat(result).isTrue();
		
	}
	
	@Test
	public void verificaExistenciaEmailFalse() {
		
//		usuarioRepository.deleteAll();
//		builder().nome("usuario").email("usuario@email.com").build();//Lombok

		//acao/execucao
		boolean result = usuarioRepository.existsByEmail("usuario@email.com");
		
		//verificacao
		//Assertions - classe de testes do spring
		Assertions.assertThat(result).isFalse();
		
	}

	@Test
	public void testaPersistenciaUsuarioBD() {
		
		//cenario
		Usuario usuario = criarUsuario();
		
		//acao
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		//verificacao
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
		
	}
	
	@Test
	public void buscaUsuarioPorEmailTrue() {
		
		Usuario usuario = criarUsuario() ;
		entityManager.persist(usuario);
		
		Optional<Usuario> result = usuarioRepository.findByEmail("usuario@email.com");
		
		Assertions.assertThat(result.isPresent()).isTrue();
	}
	
	//retorna false se não existe o email na base
	@Test
	public void buscaUsuarioPorEmailFalse() {
		
		Optional<Usuario> result = usuarioRepository.findByEmail("usuario@email.com");
		
		Assertions.assertThat(result.isPresent()).isFalse();
		
	}
	
	public static Usuario criarUsuario() {
		return new Usuario("usuario","usuario@email.com","senha");
	}
	
	
}
