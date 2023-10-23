package br.com.serragram.serragram.model;

import java.text.SimpleDateFormat;
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

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_comentario")
	private Long id;

	@Column
	@ApiModelProperty(value = "Comentário do Post")
	private String texto;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao")
	@ApiModelProperty(value = "Data de criação do Comentário")
	private Calendar dataCriacao;

	@ManyToOne
	@JoinColumn(name = "post_id")
	@JsonBackReference
	@ApiModelProperty(value = "Id do Post")
	private Post post;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	@ApiModelProperty(value = "Id do autor do comentário")
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

	public Calendar getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Calendar dataCriacao) {
		this.dataCriacao = dataCriacao;
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
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return "Comentário: " + texto
				+"\nData Criaçao: " + sdf.format(dataCriacao.getTime())
				+"\n\nPost: " + post.getConteudo()
				+"\nData Criação do Post: " + sdf.format(post.getDataCriacao().getTime());
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
