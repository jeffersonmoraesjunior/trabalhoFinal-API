package br.com.serragram.serragram.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Relationship {

	@EmbeddedId
	private RelationshipPK id = new RelationshipPK();

	@Column(name = "data_inicio_seguimento")
	private Date dataInicioSeguimento;

	@ManyToOne
	@JoinColumn(name = "id_user_seguidor", insertable = false, updatable = false)
	@JsonBackReference
	private User seguidor;
	
	@ManyToOne
	@JoinColumn(name = "id_user_seguido", insertable = false, updatable = false)
	@JsonBackReference
	private User seguido;

	public RelationshipPK getId() {
		return id;
	}

	public void setId(RelationshipPK id) {
		this.id = id;
	}

	public Date getDataInicioSeguimento() {
		return dataInicioSeguimento;
	}

	public void setDataInicioSeguimento(Date dataInicioSeguimento) {
		this.dataInicioSeguimento = dataInicioSeguimento;
	}

	public User getSeguidor() {
		return seguidor;
	}

	public void setSeguidor(User seguidor) {
		this.seguidor = seguidor;
	}

	public User getSeguido() {
		return seguido;
	}

	public void setSeguido(User seguido) {
		this.seguido = seguido;
	}
	
	

}
