package br.com.serragram.serragram.DTO;

public class PostInserirDTO {

	private String conteudo;
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
