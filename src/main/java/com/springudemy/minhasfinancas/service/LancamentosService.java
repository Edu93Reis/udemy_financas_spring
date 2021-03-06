package com.springudemy.minhasfinancas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.springudemy.minhasfinancas.model.entity.Lancamento;
import com.springudemy.minhasfinancas.model.enums.StatusLancamento;

public interface LancamentosService {
	
	Lancamento salvar(Lancamento lancamento);
	Lancamento atualizar(Lancamento lancamento);
	void deletar(Lancamento lancamento);
	List<Lancamento> buscar(Lancamento lancamento);
	void atualizarStatus(Lancamento lancamento, StatusLancamento status);
	void validar(Lancamento lancamento);
	Optional<Lancamento> obterPorId(Long id);
	BigDecimal obterSaldoPorUsuario(Long id);

}
