package br.com.serragram.serragram.DTO;

import java.util.Date;

import javax.validation.constraints.Email;

import br.com.serragram.serragram.model.User;

public class UserAtualizarDTO {

	
	private String nome;
	
	private String sobreNome;
	
	private Date dataNascimento;

	@Email(message = "Preencha um Email Válido")
	private String email;
	
	public UserAtualizarDTO() {}
	
	public UserAtualizarDTO(User user) {
		this.nome = user.getNome();
		this.sobreNome = user.getSobreNome();
		this.dataNascimento = user.getDataNascimento();
		this.email = user.getEmail();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobreNome() {
		return sobreNome;
	}

	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
