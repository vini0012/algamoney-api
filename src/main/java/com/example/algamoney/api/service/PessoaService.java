package com.example.algamoney.api.service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Componente do Spring
@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pessoa salvar(Pessoa pessoa) {
        pessoa.getContatos().forEach(c -> c.setPessoa(pessoa));
        return pessoaRepository.save(pessoa);

    }
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);

        pessoaSalva.getContatos().clear();
        pessoaSalva.getContatos().addAll(pessoa.getContatos());
        pessoaSalva.getContatos().forEach(c -> c.setPessoa(pessoaSalva));

		//copio as propriedades e ignoro outras se quiser
        BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo", "contatos");
		
		return pessoaRepository.save(pessoaSalva); 
	}

	public void atualizarPropriedadeAtivo(Long codigo) {
		pessoaRepository.updateAtivoById(codigo);
	}
	
	public Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Pessoa pessoaSalva = pessoaRepository.findById(codigo)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));

        return pessoaSalva;
	}
}
