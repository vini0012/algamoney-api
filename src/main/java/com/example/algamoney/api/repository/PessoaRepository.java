package com.example.algamoney.api.repository;

import com.example.algamoney.api.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	Page<Pessoa> findByNomeContaining(String nome, Pageable pageable);

	@Modifying
	@Transactional
	@Query(value = "update PESSOA p set p.ativo = !p.ativo where p.codigo = :codigo", nativeQuery = true)
	void updateAtivoById(@Param("codigo") Long codigo);
}
