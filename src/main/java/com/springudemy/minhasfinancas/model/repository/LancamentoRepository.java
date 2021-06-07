package com.springudemy.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springudemy.minhasfinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
