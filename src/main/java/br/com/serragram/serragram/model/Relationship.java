package br.com.serragram.serragram.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Relationship {

	@EmbeddedId
	@ApiModelProperty(value = "Chave Primária composta de relação de usuários")
	private RelationshipPK id = new RelationshipPK();

	@Temporal(TemporalType.DATE)
	@Column(name = "data_inicio_seguimento")
	@ApiModelProperty(value = "Data de início da relação seguidor/seguido")
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
		Relationship other = (Relationship) obj;
		return Objects.equals(id, other.id);
	}	
	
}
