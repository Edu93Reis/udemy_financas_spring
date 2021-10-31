package com.springudemy.minhasfinancas.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springudemy.minhasfinancas.api.dto.AtualizaStatusDTO;
import com.springudemy.minhasfinancas.api.dto.LancamentoDTO;
import com.springudemy.minhasfinancas.exceptions.RegraNegocioException;
import com.springudemy.minhasfinancas.model.entity.Lancamento;
import com.springudemy.minhasfinancas.model.entity.Usuario;
import com.springudemy.minhasfinancas.model.enums.StatusLancamento;
import com.springudemy.minhasfinancas.model.enums.TipoLancamento;
import com.springudemy.minhasfinancas.service.LancamentosService;
import com.springudemy.minhasfinancas.service.UsuarioService;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {
	
	private LancamentosService service;
	private UsuarioService usuarioService;
	
	public LancamentoController(LancamentosService service, UsuarioService usuarioService) {
		this.service = service;
		this.usuarioService = usuarioService;
	}

	@PostMapping
	public ResponseEntity salvar( @RequestBody LancamentoDTO dto) {
		
		try {
			Lancamento entidade = converter(dto);
			entidade = service.salvar(entidade);
			
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		}catch(RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PutMapping("{id}") //pega o id da url para jogar na variavel Long id
	public ResponseEntity atualizar( @PathVariable("id") Long id, @RequestBody LancamentoDTO dto) {
		return service.obterPorId(id).map( entity -> {
				try {
					Lancamento lancamento = converter(dto);
					lancamento.setId(entity.getId());
					service.atualizar(lancamento);
					return ResponseEntity.ok(lancamento);
				}catch(RegraNegocioException e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}
			}).orElseGet( () -> new ResponseEntity("Lancamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}
	
	@PutMapping("{id}/atualiza-status")
	public ResponseEntity atualizarStatus( @PathVariable("id") Long id, @RequestBody AtualizaStatusDTO dto ) {
		return service.obterPorId(id).map( entity -> {
			StatusLancamento statusSelecionado = StatusLancamento.valueOf(dto.getStatus());
			
			if(statusSelecionado == null) {
				return ResponseEntity.badRequest().body("Não foi possível atualizar o status do lancamento, envie um status válido.");
			}
			
			try {
				entity.setStatus(statusSelecionado);
				service.atualizar(entity);
				return ResponseEntity.ok(entity);
			}catch(RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			
		}).orElseGet( () -> new ResponseEntity("Lancamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar( @PathVariable("id") Long id) {
		return service.obterPorId(id).map( entidade -> {
					service.deletar(entidade);
					return new ResponseEntity( HttpStatus.NO_CONTENT);
				}).orElseGet( () ->
							new ResponseEntity("Lancamento não encontrado na base de dados", HttpStatus.BAD_REQUEST)
						);
	}
	
	@GetMapping
	public ResponseEntity buscar(
				@RequestParam(value = "descricao", required = false) String descricao, //opcional
				@RequestParam(value = "mes", required = false) Integer mes, //opcional
				@RequestParam(value = "ano", required = false) Integer ano, //opcional
				@RequestParam("usuario") Long idUsuario //obrigatorio
			) {
		Lancamento lancamentoFiltro = new Lancamento();
		lancamentoFiltro.setDescricaoLancamento(descricao);
		lancamentoFiltro.setAno(ano);
		lancamentoFiltro.setMes(mes);
		
		Optional<Usuario> usuario = usuarioService.obterPorId(idUsuario);
		
		if(usuario.isPresent()) {
			return ResponseEntity.badRequest().body("Usuário não encontrado. Usuário não encontrado para o id encontrado!");
		}else {
			lancamentoFiltro.setUsuario(usuario.get());
		}
	
		List<Lancamento> lancamentos = service.buscar(lancamentoFiltro);
		
		return ResponseEntity.ok(lancamentos);
		
	}
	
	private Lancamento converter(LancamentoDTO dto) {
		Lancamento lancamento = new Lancamento();
		
		lancamento.setDescricaoLancamento(dto.getDescricao());
		lancamento.setAno(dto.getAno());
		lancamento.setMes(dto.getMes());
		lancamento.setValor(dto.getValor());
		
		Usuario usuario = usuarioService.obterPorId(dto.getUsuario())
					  .orElseThrow( () -> new RegraNegocioException("Usuário não encontrado para o id encontrado!"));
		
		lancamento.setUsuario(usuario);
		lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
		lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
		return lancamento;
	}
	
}
