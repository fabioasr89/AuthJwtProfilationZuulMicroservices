package com.fabio.microservices.security.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

@Entity
@Table(name="ruolo")
public class Ruolo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="nome")
	private String nome;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name="ruoli_funzioni",
		joinColumns= {@JoinColumn(name="id_ruolo")},
		inverseJoinColumns= {@JoinColumn(name="id_funzione")}
	)
	private Set<Funzione> funzioni;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Funzione> getFunzioni() {
		return funzioni;
	}

	public void setFunzioni(Set<Funzione> funzioni) {
		this.funzioni = funzioni;
	}
	
	
}
