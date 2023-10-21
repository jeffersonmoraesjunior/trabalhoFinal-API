package br.com.serragram.serragram.DTO;

import br.com.serragram.serragram.model.Relationship;

public class RelationshipDTO {

	private String nomeSeguidor;
	
	private String nomeSeguido;
	
	public RelationshipDTO(Relationship relationship) {
		this.nomeSeguidor = relationship.getId().getUserSeguidor().getNome();
		this.nomeSeguido = relationship.getId().getUserSeguido().getNome();
		
	}

	public String getNomeSeguidor() {
		return nomeSeguidor;
	}

	public void setNomeSeguidor(String nomeSeguidor) {
		this.nomeSeguidor = nomeSeguidor;
	}

	public String getNomeSeguido() {
		return nomeSeguido;
	}

	public void setNomeSeguido(String nomeSeguido) {
		this.nomeSeguido = nomeSeguido;
	}
	
	
	
}
