package br.com.serragram.serragram.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Relationship {

	@EmbeddedId
	private RelationshipPK id = new RelationshipPK();

	@Temporal(TemporalType.DATE)
	@Column(name = "data_inicio_seguimento")
	private Date dataInicioSeguimento;
	
	

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


	

}
