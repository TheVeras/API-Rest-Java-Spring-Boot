package com.veras.rodrigo.model;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data	
@Entity
@Table(name = "pessoa")
public class Pessoa {
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "pessoa_generator")
	private long id;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	@Column(name = "dataNascimento")
	private Date dataNascimento; 	
	
	public Pessoa() {
		
	}
	
	public Pessoa(String nome, Date dataNascimento) {
		this.nome = nome;
		this.dataNascimento = dataNascimento;
	}
}
