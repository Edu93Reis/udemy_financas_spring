package com.springudemy.minhasfinancas.model.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

//import static org.assertj.core.api.Assertions.*;
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

import com.springudemy.minhasfinancas.model.entity.Lancamento;
import com.springudemy.minhasfinancas.model.enums.StatusLancamento;
import com.springudemy.minhasfinancas.model.enums.TipoLancamento;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

	@Autowired
	LancamentoRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void deveSalvarUmLancamento() {
		Lancamento lancamento = criarEPersistirUmLancamento();
		
		Assertions.assertThat(lancamento.getId()).isNotNull();
	}

	private Lancamento criarEPersistirUmLancamento() {
		Lancamento lancamento = criarLancamento();
		lancamento = repository.save(lancamento);
		return lancamento;
	}

	@Test
	public void deveAtualizarUmLancamento() {
		Lancamento lancamento = criarEPersistirUmLancamento();
		
		lancamento.setAno(2018);
		lancamento.setDescricaoLancamento("Teste Atualizar");
		lancamento.setStatus(StatusLancamento.CANCELADO);
		
		repository.save(lancamento);
		
		Lancamento lancamentoAtualizado = entityManager.find(Lancamento.class, lancamento.getId());
		
		Assertions.assertThat(lancamentoAtualizado.getAno()).isEqualTo(2018);
		Assertions.assertThat(lancamentoAtualizado.getDescricaoLancamento()).isEqualTo("Teste Atualizar");
		Assertions.assertThat(lancamentoAtualizado.getStatus()).isEqualTo(StatusLancamento.CANCELADO);
	}
	
	@Test
	public void deveBuscarUmLancamentoPorId() {
		Lancamento lancamento = criarEPersistirUmLancamento();
		
		Optional<Lancamento> lancamentoEncontrado = repository.findById(lancamento.getId());
		
		Assertions.assertThat(lancamentoEncontrado.isPresent()).isTrue();
	}
	
	private Lancamento criarLancamento() {
		return new Lancamento("Lancamento Qualquer", 1, 2019, BigDecimal.valueOf(10), LocalDate.now(), TipoLancamento.RECEITA, StatusLancamento.PENDENTE);
	}
	
	@Test
	public void deveDeletarUmLancamento() {
		Lancamento lancamento = criarLancamento();
		entityManager.persist(lancamento);
		
		lancamento = entityManager.find(Lancamento.class, lancamento.getId());
		
		repository.delete(lancamento);
		
		Lancamento lancamentoInexistente = entityManager.find(Lancamento.class, lancamento.getId());
		
		Assertions.assertThat(lancamentoInexistente).isNull();
		
	}
	
}
