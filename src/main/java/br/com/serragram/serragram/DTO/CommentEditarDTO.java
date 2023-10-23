package br.com.serragram.serragram.DTO;

import javax.validation.constraints.NotBlank;

public class CommentEditarDTO {

	@NotBlank(message = "Insira um Texto")
	private String texto;

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	
}
