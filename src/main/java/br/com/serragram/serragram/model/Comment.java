package br.com.serragram.serragram.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_post")
	private Long id;

	@NotBlank(message = "insira um texto:")
	@Column
	private String texto;

	@Column(name = "data_criacao")
	private Date dataCriaçao;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	public Comment() {
	}

	public Comment(Long id, @NotBlank(message = "insira um texto:") String texto, Date dataCriaçao) {
		this.id = id;
		this.texto = texto;
		this.dataCriaçao = dataCriaçao;
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
		Comment other = (Comment) obj;
		return Objects.equals(id, other.id);
	}

}
