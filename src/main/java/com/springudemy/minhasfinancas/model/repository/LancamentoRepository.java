package com.springudemy.minhasfinancas.model.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springudemy.minhasfinancas.model.entity.Lancamento;
import com.springudemy.minhasfinancas.model.enums.StatusLancamento;
import com.springudemy.minhasfinancas.model.enums.TipoLancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

	@Query(value = "SELECT SUM(L.VALOR) FROM LANCAMENTO L JOIN L.usuario U "
			       + "WHERE U.ID = :idUsuario AND l.tipo = :tipo and l.status = :status GROUP BY U ") //permite passar consulta sql
	BigDecimal obterSaldoPorTipoLancamentoEUsuarioEStatus(
			@Param("idUsuario") Long idUsuario, 
			@Param("tipo") TipoLancamento tipo,
			@Param("status") StatusLancamento status);
	
}
