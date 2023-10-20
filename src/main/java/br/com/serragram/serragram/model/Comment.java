package br.com.serragram.serragram.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_comentario")
	private Long id;

	@NotBlank(message = "insira um texto:")
	@Column
	private String texto;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao")
	private Calendar dataCriaçao;

	@ManyToOne
	@JoinColumn(name = "post_id")
	@JsonBackReference
	private Post post;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User autorComentario;

	public Comment() {
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
		
	public User getAutorComentario() {
		return autorComentario;
	}

	public void setAutorComentario(User autorComentario) {
		this.autorComentario = autorComentario;
	}

	@Override
	public String toString() {
		return "Comentário: " + texto
				+"Data Criaçao: " + dataCriaçao
				+"Post: " + post;
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
