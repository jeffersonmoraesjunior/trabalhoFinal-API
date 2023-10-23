package br.com.serragram.serragram.DTO;

import javax.validation.constraints.NotBlank;

public class PostEditarDTO {

	@NotBlank(message = "Insira um conteúdo")
	private String conteudo;
	

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	
}
