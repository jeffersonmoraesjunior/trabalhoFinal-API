package br.com.serragram.serragram.DTO;

import java.util.Calendar;

import br.com.serragram.serragram.model.Comment;
import br.com.serragram.serragram.model.Post;

public class CommentDTO {
	
	private Long id;

	private String texto;
	
	private Calendar dataCriaçao;
	
	private Post post;
	
	public CommentDTO() {}
	
	public CommentDTO(Comment comment) {
		this.id = comment.getId();
		this.texto = comment.getTexto();
		this.dataCriaçao = comment.getDataCriaçao();
		this.post = comment.getPost();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Calendar getDataCriaçao() {
		return dataCriaçao;
	}

	public void setDataCriaçao(Calendar dataCriaçao) {
		this.dataCriaçao = dataCriaçao;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	
}
