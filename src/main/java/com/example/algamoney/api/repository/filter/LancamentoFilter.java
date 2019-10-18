package com.example.algamoney.api.repository.filter;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Data
public class LancamentoFilter {
	
	private String descricao;
	private LocalDate dataVencimentoDe;
	private LocalDate dataVencimentoAte;
	private Integer page;
	private Integer size;

	public Pageable getPageable() {
		return PageRequest.of(page, size);
	}



}
