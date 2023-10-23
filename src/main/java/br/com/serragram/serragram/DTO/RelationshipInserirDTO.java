package br.com.serragram.serragram.DTO;

import javax.validation.constraints.NotNull;

public class RelationshipInserirDTO {
	
	@NotNull(message = "Insira o id do seguidor")
	private Long seguidorId;
	
	@NotNull(message = "Insira o id do seguido")
	private Long seguidoId;

	public Long getSeguidorId() {
		return seguidorId;
	}

	public void setSeguidorId(Long seguidorId) {
		this.seguidorId = seguidorId;
	}

	public Long getSeguidoId() {
		return seguidoId;
	}

	public void setSeguidoId(Long seguidoId) {
		this.seguidoId = seguidoId;
	}
	
	
}
