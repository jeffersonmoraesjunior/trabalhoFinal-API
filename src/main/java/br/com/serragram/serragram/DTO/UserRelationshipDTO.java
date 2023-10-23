package br.com.serragram.serragram.DTO;

import java.util.Date;

public interface UserRelationshipDTO {

	public Long getSeguidorId();
	public String getNome();
	public String getSobreNome();
	public String getEmail();
	public Date getDataNascimento();
	public Date getDataInicioSeguimento();
}
