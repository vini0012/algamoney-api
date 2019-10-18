package com.example.algamoney.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.filter.LancamentoFilter;
import com.example.algamoney.api.repository.projecao.ResumoLancamento;

public interface LancamentoRepositoryQuery {
	
	Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter);

}
