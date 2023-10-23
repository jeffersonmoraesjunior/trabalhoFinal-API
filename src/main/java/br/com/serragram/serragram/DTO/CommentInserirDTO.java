package br.com.serragram.serragram.DTO;

import javax.validation.constraints.NotBlank;

public class CommentInserirDTO {
	@NotBlank(message = "Insira um texto")
	private String texto;
	@NotBlank(message = "Insira o id do Post")
	private Long postId;
	@NotBlank(message = "Insira o id do autor")
	private Long autorId;

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Long getAutorId() {
		return autorId;
	}

	public void setAutorId(Long autorId) {
		this.autorId = autorId;
	}
	
}
