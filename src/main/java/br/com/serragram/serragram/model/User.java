package br.com.serragram.serragram.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private Long id;
	
	@NotBlank(message = "Preencha o nome")
	@Column(length = 60, nullable = false)
	private String nome;
	
	@NotBlank(message = "Preencha o Sobrenome")
	@Column(length = 60, nullable = false)
	private String sobreNome;
	
	@NotBlank(message = "Preencha o Email")
	@Email(message = "Preencha o Email Válido")
	@Column(length = 60, nullable = false)
	private String email;
	
	@NotBlank(message = "Preencha a Senha")
	@Column(length = 60, nullable = false)
	private String senha;
	
	@NotBlank(message = "Preencha a data de Nascimento")
	@Column(length = 60, nullable = false)
	private Date dataNascimento;
	
	public User() {
	
	}
	public User(Long id, String nome, String sobreNome, String email, String senha, Date dataNascimento) {
		this.id = id;
		this.nome = nome;
		this.sobreNome = sobreNome;
		this.email = email;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
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
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}	

}
