package com.example.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.algamoney.api.model.Categoria;

//Recebe um Long como chave primaria e retorna uma categoria
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
