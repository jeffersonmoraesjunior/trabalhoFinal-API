package br.com.serragram.serragram.DTO;

import java.util.Calendar;

import br.com.serragram.serragram.model.Post;

public class PostDTO {
	
	private Long id;
	
	private String conteudo;	
	
	private Calendar dataCriacao;
	
	private Long autorId;
	
	private String nomeAutor;
	
	private String sobrenomeAutor;
	
	public PostDTO() {}
	
	public PostDTO(Post post) {
		this.id = post.getId();
		this.conteudo = post.getConteudo();
		this.dataCriacao = post.getDataCriacao();
		this.autorId = post.getAutor().getId();
		this.nomeAutor = post.getAutor().getNome();
		this.sobrenomeAutor = post.getAutor().getSobreNome();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public Calendar getDataCricao() {
		return dataCriacao;
	}
	
	public void setDataCricao(Calendar dataCricao) {
		this.dataCriacao = dataCricao;
	}

	public Long getAutorId() {
		return autorId;
	}

	public void setAutorId(Long autorId) {
		this.autorId = autorId;
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

	

}
