package com.veras.rodrigo.controller;

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
import org.springframework.web.bind.annotation.RestController;
import com.veras.rodrigo.model.Endereco;
import com.veras.rodrigo.repository.EnderecoRepository;
import com.veras.rodrigo.repository.PessoaRepository;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class EnderecoController {
		
		@Autowired
		private PessoaRepository pessoaRepository;

		@Autowired
		private EnderecoRepository enderecoRepository;
		
		@GetMapping("/pessoas-endereco/{pessoaId}")
		public ResponseEntity<List<Endereco>> getAllEnderecosByPessoaId(@PathVariable(value = "PESSOA_ID") Long pessoaId) {
			if (!pessoaRepository.existsById(pessoaId)) {
		    	throw new Error("Erro ao encontrar Pessoa");
		    }
			List<Endereco> enderecos = enderecoRepository.findByPessoaId(pessoaId);
		    return new ResponseEntity<>(enderecos, HttpStatus.OK);
		}
		
		@GetMapping("/enderecos/{id}")
		public ResponseEntity<Endereco> getEnderecosById(@PathVariable(value = "id") Long id) {
			Endereco endereco = enderecoRepository.findById(id)
		        .orElseThrow(() -> new Error("Não encontramos o endereco com id = " + id));

		    return new ResponseEntity<>(endereco, HttpStatus.OK);
		}
		
		@PostMapping("/pessoas/{pessoaId}/enderecos-cadastro")
		public ResponseEntity<Endereco> createEndereco(@PathVariable(value = "pessoaId") Long pessoaId,
		    @RequestBody Endereco enderecoRequest) {
		    Endereco endereco = pessoaRepository.findById(pessoaId).map(pessoa -> {
		      enderecoRequest.setPessoa(pessoa);
		      return enderecoRepository.save(enderecoRequest);
		    }).orElseThrow(() -> new Error("Não encontramos a pessoa com o id = " + pessoaId));

		    return new ResponseEntity<>(endereco, HttpStatus.CREATED);
		}
		
		@PutMapping("/enderecos/{id}")
		public ResponseEntity<Endereco> updateEndereco(@PathVariable("id") long id, @RequestBody Endereco enderecoRequest) {
		    Endereco endereco = enderecoRepository.findById(id)
		        .orElseThrow(() -> new Error("Endereco id = " + id + "Não encontrado"));

		    endereco.setLogradouro(enderecoRequest.getLogradouro());
		    endereco.setNumero(enderecoRequest.getNumero());
		    endereco.setCidade(enderecoRequest.getCidade());
		    endereco.setCep(enderecoRequest.getCep());

		    return new ResponseEntity<>(enderecoRepository.save(endereco), HttpStatus.OK);
		}
		@DeleteMapping("/endereco/{id}")
		public ResponseEntity<HttpStatus> deleteendereco(@PathVariable("id") long id) {
		    enderecoRepository.deleteById(id);

		    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		@DeleteMapping("/pessoas/{pessoaId}/enderecos")
		public ResponseEntity<List<Endereco>> deleteAllEnderecosOfPessoa(@PathVariable(value = "pessoaId") Long pessoaId) {
		    if (!pessoaRepository.existsById(pessoaId)) {
		      throw new Error("Não encontramos pessoa com o id = " + pessoaId);
		    }

		    enderecoRepository.deleteByPessoaId(pessoaId);
		    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
}
