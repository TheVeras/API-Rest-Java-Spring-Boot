package com.veras.rodrigo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veras.rodrigo.model.Pessoa;


public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	List<Pessoa> findByNome(String nome);
}
