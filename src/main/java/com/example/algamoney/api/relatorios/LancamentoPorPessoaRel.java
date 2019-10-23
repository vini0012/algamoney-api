package com.example.algamoney.api.relatorios;

import com.example.algamoney.api.dto.LancamentoEstatisticaPessoa;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class LancamentoPorPessoaRel {

    private String tipo;
    private String pessoa;
    private BigDecimal total;

    public LancamentoPorPessoaRel(LancamentoEstatisticaPessoa lancamento) {
        this.tipo = lancamento.getTipo().name();
        this.pessoa = lancamento.getPessoa().getNome();
        this.total = lancamento.getTotal();
    }
}
