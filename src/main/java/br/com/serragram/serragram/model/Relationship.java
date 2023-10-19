package br.com.serragram.serragram.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Relationship {
	
	@EmbeddedId
	private RelationshipPK id = new RelationshipPK();
	
	@Column(name = "data_inicio_seguimento")
	private Date dataInicioSeguimento;

	public Relationship(RelationshipPK id, Date dataInicioSeguimento) {
		super();
		this.id = id;
		this.dataInicioSeguimento = dataInicioSeguimento;
	}

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
