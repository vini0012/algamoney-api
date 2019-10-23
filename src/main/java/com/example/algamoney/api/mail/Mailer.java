package com.example.algamoney.api.mail;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Usuario;
import com.example.algamoney.api.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Mailer {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine thymeleaf;

    @Autowired
    private LancamentoRepository repo;

//    @EventListener
//    public void teste(ApplicationReadyEvent event) {
//        this.enviarEmail("marcos.antonio@viasoft.com",
//                Arrays.asList("viniscote@gmail.com"),
//                "Testando",
//                "Olá! <br/> Teste ok.");
//        System.out.println("Terminado o envio de email...");
//    }

//    @EventListener
//    public void teste(ApplicationReadyEvent event) {
//        String template = "mail/aviso-lancamentos-vencidos";
//
//        List<Lancamento> lista = repo.findAll();
//
//        Map<String, Object> variaveis = new HashMap<>();
//        variaveis.put("lancamentos", lista);
//
//        this.enviarEmail("marcos.antonio@viasoft.com",
//                Arrays.asList("viniscote@gmail.com"),
//                "Testando",
//                template, variaveis);
//        System.out.println("Terminado o envio de email...");
//    }

    public void avisarSobreLancamentosVencidos(List<Lancamento> vencidos, List<Usuario> destinatarios) {
        Map<String, Object> variaveis = new HashMap<>();
        variaveis.put("lancamentos", vencidos);

        List<String> emails = destinatarios.stream()
                .map(Usuario::getEmail)
                .collect(Collectors.toList());

        this.enviarEmail(emails,
                variaveis);
    }

    private void enviarEmail(List<String> destinatarios,
                             Map<String, Object> variaveis) {

        Context context = new Context(new Locale("pt", "BR"));

        variaveis.forEach(context::setVariable);

        String mensagem = thymeleaf.process("mail/aviso-lancamentos-vencidos", context);

        this.enviarEmail("marcos.antonio@viasoft.com", destinatarios, "Lançamentos vencidos", mensagem);
    }


    public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String mensagem) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setFrom(remetente);
            helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
            helper.setSubject(assunto);
            helper.setText(mensagem, true);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Problemas com o envio de e-mail!", e);
        }
    }
}
