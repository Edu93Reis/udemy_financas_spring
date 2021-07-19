package com.springudemy.minhasfinancas.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springudemy.minhasfinancas.api.dto.UsuarioDTO;
import com.springudemy.minhasfinancas.exceptions.ErroAutenticacaoException;
import com.springudemy.minhasfinancas.model.entity.Usuario;
import com.springudemy.minhasfinancas.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	private UsuarioService usuarioService;
	
	private UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@PostMapping("/autenticar")
	public ResponseEntity autenticar( @RequestBody UsuarioDTO dto ) {
		
		try {
			Usuario usuarioAutenticado = usuarioService.autenticar(dto.getEmail(), dto.getSenha());
			
			return ResponseEntity.ok(usuarioAutenticado);
		}catch(ErroAutenticacaoException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity salvar( @RequestBody UsuarioDTO dto ) {
		
		Usuario usuario = new Usuario(dto.getNome(), dto.getEmail(), dto.getSenha());
		
		try {
			Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);
			
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}

}