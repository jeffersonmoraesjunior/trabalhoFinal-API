package br.com.serragram.serragram.DTO;

import java.util.Calendar;

import br.com.serragram.serragram.model.Comment;

public class CommentDTO {
	
	private Long id;

	private String nomeAutor;
	
	private String sobrenomeAutor;
	
	private String texto;
	
	private Calendar dataCriacao;

	private Long postId;
	
	private String nomeAutorPost;
	
	private String sobrenomeAutorPost;
	
	private String conteudo;
	
	private Calendar dataCriacaoPost;
	
	
	public CommentDTO() {}
	
	public CommentDTO(Comment comment) {
		this.id = comment.getId();
		this.nomeAutor = comment.getAutorComentario().getNome();
		this.sobrenomeAutor = comment.getAutorComentario().getSobreNome();
		this.texto = comment.getTexto();
		this.dataCriacao = comment.getDataCriacao();
		this.postId = comment.getPost().getId();
		this.nomeAutorPost = comment.getPost().getAutor().getNome();
		this.sobrenomeAutorPost = comment.getPost().getAutor().getSobreNome();
		this.conteudo = comment.getPost().getConteudo();
		this.dataCriacaoPost = comment.getPost().getDataCriacao();
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

	public Calendar getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Calendar dataCriaçao) {
		this.dataCriacao = dataCriaçao;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public void setNomeAutor(String nomeAutor) {
		this.nomeAutor = nomeAutor;
	}

	public String getSobrenomeAutor() {
		return sobrenomeAutor;
	}

	public void setSobrenomeAutor(String sobrenomeAutor) {
		this.sobrenomeAutor = sobrenomeAutor;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getNomeAutorPost() {
		return nomeAutorPost;
	}

	public void setNomeAutorPost(String nomeAutorPost) {
		this.nomeAutorPost = nomeAutorPost;
	}

	public String getSobrenomeAutorPost() {
		return sobrenomeAutorPost;
	}

	public void setSobrenomeAutorPost(String sobrenomeAutorPost) {
		this.sobrenomeAutorPost = sobrenomeAutorPost;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Calendar getDataCriacaoPost() {
		return dataCriacaoPost;
	}

	public void setDataCriacaoPost(Calendar dataCriacaoPost) {
		this.dataCriacaoPost = dataCriacaoPost;
	}
	
	
}
