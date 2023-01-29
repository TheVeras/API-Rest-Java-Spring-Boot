package com.veras.rodrigo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veras.rodrigo.model.Endereco;

import jakarta.transaction.Transactional;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
	List<Endereco> findByPessoaId(Long pessoaId);
	
	@Transactional
	void deleteByPessoaId(long pessoaId);
}
