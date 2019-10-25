package com.example.algamoney.api.service;

import com.example.algamoney.api.mail.Mailer;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.model.Usuario;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.repository.UsuarioRepository;
import org.assertj.core.util.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LancamentoServiceTest {

    //Cria um objeto falso, ou seja, não exclui do banco
    @Mock
    private LancamentoRepository lancamentoRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private LancamentoService lancamentoService;

    @Mock
    private Mailer mailer;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testarEnvioEmailSobreLancamentosVencidos() {

        doReturn(Lists.list(new Lancamento()))
                .when(lancamentoRepository)
                .findByDataVencimentoLessThanEqualAndDataPagamentoIsNull((any(LocalDate.class)));

        doReturn(Lists.list(new Usuario()))
                .when(usuarioRepository)
                .findByPermissoesDescricao(any());

        lancamentoService.avisarSobreLancamentosVencidos();
        verify(mailer, times(1)).avisarSobreLancamentosVencidos(any(), any());
    }

    @Test
    public void testarPessoaParaSalvar() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("Lançamento não pode ser nulo");

        Lancamento lancamento = new Lancamento();
        lancamento.setPessoa(new Pessoa(1L));
        lancamentoService.validarPessoa(lancamento);
    }

    @Test
    public void testarBuscaDeLancamentoExistente() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("Lançamento não encontrado");

        Lancamento lancamento = new Lancamento(88L);
        lancamentoService.buscarLancamentoExistente(lancamento.getCodigo());
    }
}
