package br.com.serragram.serragram.DTO;

public class CommentInserirDTO {

	private String texto;
	
	private Long postId;
	
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
