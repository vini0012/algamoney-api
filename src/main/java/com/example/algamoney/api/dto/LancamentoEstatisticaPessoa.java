package com.example.algamoney.api.dto;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.model.TipoLancamento;

import java.math.BigDecimal;

public class LancamentoEstatisticaPessoa {

    private TipoLancamento tipo;

    private Pessoa pessoa;

    private BigDecimal total;

    public LancamentoEstatisticaPessoa(TipoLancamento tipo, Pessoa pessoa, BigDecimal total) {
        this.tipo = tipo;
        this.pessoa = pessoa;
        this.total = total;
//
//        List<LancamentoEstatisticaPessoa> lancamentoEstatisticaPessoas = new ArrayList<>();
//
//        List<LancamentoPorPessoaRel> collect = lancamentoEstatisticaPessoas.stream()
//                .map(LancamentoPorPessoaRel::new)
//                .collect(Collectors.toList());
//
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
