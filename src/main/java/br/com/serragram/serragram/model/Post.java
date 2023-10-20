package br.com.serragram.serragram.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	@Temporal(TemporalType.TIMESTAMP)
	//@JsonFormat(pattern = "dd/MM/yyyy")
	private Calendar dataCriacao;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User autor;
	
	@OneToMany(mappedBy = "post")
	@JsonManagedReference
	private List<Comment> comentarios;

	
	public Post() {}
	
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

	public Calendar getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Calendar dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	

	public User getAutor() {
		return autor;
	}

	public void setAutor(User autor) {
		this.autor = autor;
	}

	public List<Comment> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comment> comentarios) {
		this.comentarios = comentarios;
	}
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	@Override
	public String toString() {
		return "Post: " + conteudo 
				+"Data Criaçao: " + sdf.format(dataCriacao)
				+"Autor: " + autor;
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
