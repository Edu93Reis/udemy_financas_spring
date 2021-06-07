package com.springudemy.minhasfinancas.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springudemy.minhasfinancas.model.entity.Usuario;

//<Tipo da Classe, Tipo da Chave>
//não precisa de implementação pois já recebe da JpaRepository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	//Optional retorna true se existir e vazio se não
	Optional<Usuario> findByEmail(String email);
//	Optional<Usuario> findByEmailAndNome(String email, String nome);//concatenacao
	
	boolean existsByEmail(String email);//outra forma de verificar, usar convencao exists
	
}
