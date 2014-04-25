package br.unicamp.contatos.client;

import java.io.Serializable;

public class Contato implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String email;
	private String categoria;
	private String telefone;

	public Contato()
	{
		
	}
	public Contato(String nome, String email, String categoria, String telefone) {
		super();
		this.nome = nome;
		this.email = email;
		this.categoria = categoria;
		this.telefone = telefone;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}
