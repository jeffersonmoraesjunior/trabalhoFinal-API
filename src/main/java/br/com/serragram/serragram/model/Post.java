package br.com.serragram.serragram.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_post")
	private Long id;
	
	@NotBlank(message = "insira um conteudo:")
	@Column
	private String conteudo;
	
	@Column(name = "data_criacao")
	private Date dataCriaçao;

	public Post() {}
	
	public Post(Long id, @NotBlank(message = "insira um conteudo:") String conteudo, Date dataCriaçao) {
		this.id = id;
		this.conteudo = conteudo;
		this.dataCriaçao = dataCriaçao;
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

	public Date getDataCriaçao() {
		return dataCriaçao;
	}

	public void setDataCriaçao(Date dataCriaçao) {
		this.dataCriaçao = dataCriaçao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return Objects.equals(id, other.id);
	}
	
}
