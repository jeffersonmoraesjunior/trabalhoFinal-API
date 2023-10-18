package DTO;

import java.util.Date;

import model.User;

public class UserDTO {
	
	private Long id;
	private String nome;
	private String sobreNome;
	private String email;
	private Date dataNascimento;
	
	public UserDTO() {}
	
	public UserDTO(User user) {
		this.id = user.getId();
		this.nome = user.getNome();
		this.sobreNome = user.getSobreNome();
		this.email = user.getEmail();
		this.dataNascimento = user.getDataNascimento();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

}
