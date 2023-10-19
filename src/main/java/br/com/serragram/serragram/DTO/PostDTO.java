package br.com.serragram.serragram.DTO;

import java.util.Date;

import br.com.serragram.serragram.model.Post;

public class PostDTO {
	
	private Long id;
	private String conteudo;
	private Date dataCricao;
	
	public PostDTO() {}
	
	public PostDTO(Post post) {
		this.id = post.getId();
		this.conteudo = post.getConteudo();
		this.dataCricao = post.getDataCriaçao();
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
	public Date getDataCricao() {
		return dataCricao;
	}
	public void setDataCricao(Date dataCricao) {
		this.dataCricao = dataCricao;
	}
	
	

}
