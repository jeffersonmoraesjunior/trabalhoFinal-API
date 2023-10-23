package br.com.serragram.serragram.DTO;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.serragram.serragram.model.User;


public class UserInserirDTO {
	
	@NotBlank(message = "Preencha o nome")
	private String nome;
	
	@NotBlank(message = "Preencha o Sobrenome")
	private String sobreNome;
	
	@NotNull(message = "Preencha a data de nascimento")
	private Date dataNascimento;
	
	@NotBlank(message = "Preencha o Email")
	private String email;
	
	@NotBlank(message = "Preencha a Senha")
	private String senha;

	@NotBlank(message = "Preencha a Confirma senha")
	private String confirmaSenha;
	
	private UserAlterarSenhaDTO userAlterarSenhaDTO;
	
	UserInserirDTO () {}
	
	public UserInserirDTO(User user, UserAlterarSenhaDTO userAlterarSenhaDTO) {
		this.nome = user.getNome();
		this.sobreNome = user.getSobreNome();
		this.dataNascimento = user.getDataNascimento();
		this.email = user.getEmail();
		this.senha = userAlterarSenhaDTO.getNovaSenha();
		this.confirmaSenha = userAlterarSenhaDTO.getConfirmaNovaSenha();
		
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
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getConfirmaSenha() {
		return confirmaSenha;
	}
	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}
	
	
}
