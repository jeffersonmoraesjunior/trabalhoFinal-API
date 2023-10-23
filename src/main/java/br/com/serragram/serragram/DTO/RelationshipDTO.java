package br.com.serragram.serragram.DTO;

import java.util.Date;

import br.com.serragram.serragram.model.Relationship;

public class RelationshipDTO {

	private Long seguidorID;
	
	private String nomeSeguidor;
	
	private Long seguidoID;
	
	private String nomeSeguido;
	
	private Date dataInicio;
	
	public RelationshipDTO(Relationship relationship) {
		this.seguidorID = relationship.getId().getUserSeguidor().getId();
		this.nomeSeguidor = relationship.getId().getUserSeguidor().getNome();
		this.seguidoID = relationship.getId().getUserSeguido().getId();
		this.nomeSeguido = relationship.getId().getUserSeguido().getNome();
		this.dataInicio = relationship.getDataInicioSeguimento();
	}

	public Long getSeguidorID() {
		return seguidorID;
	}
	
	public void setSeguidorID(Long seguidorID) {
		this.seguidorID = seguidorID;
	}

	public String getNomeSeguidor() {
		return nomeSeguidor;
	}

	public void setNomeSeguidor(String nomeSeguidor) {
		this.nomeSeguidor = nomeSeguidor;
	}
	
	public Long getSeguidoID() {
		return seguidoID;
	}
	
	public void setSeguidoID(Long seguidoID) {
		this.seguidoID = seguidoID;
	}

	public String getNomeSeguido() {
		return nomeSeguido;
	}

	public void setNomeSeguido(String nomeSeguido) {
		this.nomeSeguido = nomeSeguido;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
}
