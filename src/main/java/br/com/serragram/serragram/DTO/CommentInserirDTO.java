package br.com.serragram.serragram.DTO;

import br.com.serragram.serragram.model.Post;

public class CommentInserirDTO {

	private String texto;
	
	private Post post;

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	
}
