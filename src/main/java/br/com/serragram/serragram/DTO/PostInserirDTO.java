package br.com.serragram.serragram.DTO;

import br.com.serragram.serragram.model.User;

public class PostInserirDTO {

	private String conteudo;
	private User autor;
	
	public PostInserirDTO(String conteudo, User autor) {
		this.conteudo = conteudo;
		this.autor = autor;
	}


	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public User getAutor() {
		return autor;
	}

	public void setAutor(User autor) {
		this.autor = autor;
	}

	
}
