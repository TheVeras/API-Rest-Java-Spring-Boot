package com.veras.rodrigo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.veras.rodrigo.model.Pessoa;
import com.veras.rodrigo.repository.PessoaRepository;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@GetMapping("/pessoas")
	public ResponseEntity<List<Pessoa>> getAllPessoas(@RequestParam(required = false) String nome) {
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		if (nome == null)
			pessoaRepository.findAll().forEach(pessoas::add);
		else
			pessoaRepository.findByNome(nome).forEach(pessoas::add);
		
		if (pessoas.isEmpty()) {
		    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    }
		return new ResponseEntity<>(pessoas, HttpStatus.OK);
	}

	@GetMapping("/pessoas/{id}")
	public ResponseEntity<Pessoa> getPessoaById(@PathVariable("id") long id) {
		Pessoa pessoa = pessoaRepository.findById(id)
				.orElseThrow(() -> new Error("Não encontramos pessoa com id = " + id));

	    return new ResponseEntity<>(pessoa, HttpStatus.OK);
	  }
	
	@PostMapping("/pessoas/cadastro")
	public ResponseEntity<Pessoa> createPessoa(@RequestBody Pessoa pessoa) {
	    Pessoa _pessoa = pessoaRepository.save(new Pessoa(pessoa.getNome(), pessoa.getDataNascimento()));
	    return new ResponseEntity<>(_pessoa, HttpStatus.CREATED);
	  }
	
	@PutMapping("/pessoas/editar/{id}")
	public ResponseEntity<Pessoa> updatePessoa(@PathVariable("id") long id, @RequestBody Pessoa pessoa) {
	    Pessoa _pessoa = pessoaRepository.findById(id)
	        .orElseThrow(() -> new Error("Não encontramos pessoa com id = " + id));

	    _pessoa.setNome(pessoa.getNome());
	    _pessoa.setDataNascimento(pessoa.getDataNascimento());

	    
	    return new ResponseEntity<>(pessoaRepository.save(_pessoa), HttpStatus.OK);
	  }
	
	@DeleteMapping("/pessoas/deletar/{id}")
	public ResponseEntity<HttpStatus> deletePessoa(@PathVariable("id") long id) {
	    pessoaRepository.deleteById(id);
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	
	@DeleteMapping("/pessoas/deletar")
	public ResponseEntity<HttpStatus> deleteAllPessoas() {
	    pessoaRepository.deleteAll();
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
}
