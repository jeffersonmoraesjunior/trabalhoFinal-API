package br.com.serragram.serragram.DTO;

import javax.validation.constraints.NotBlank;

public class PostInserirDTO {
	@NotBlank(message = "Insira um conteudo:")
	private String conteudo;
	@NotBlank(message = "Insira o id do autor")
	private Long autorId;
	
	public PostInserirDTO(String conteudo, Long autorId) {
		this.conteudo = conteudo;
		this.autorId = autorId;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Long getAutorId() {
		return autorId;
	}

	public void setAutorId(Long autorId) {
		this.autorId = autorId;
	}
	
}
