package br.com.serragram.serragram.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "usuario")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private Long id;

	@NotBlank(message = "Preencha o nome")
	@Column(length = 60, nullable = false)
	private String nome;

	@NotBlank(message = "Preencha o Sobrenome")
	@Column(name = "sobre_nome", length = 60, nullable = false)
	private String sobreNome;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento", nullable = false)
	private Date dataNascimento;
	
	@NotBlank(message = "Preencha o Email")
	@Email(message = "Preencha o Email Válido")
	@Column(length = 60, nullable = false)
	private String email;	
	

	@NotBlank(message = "Preencha a Senha")
	@Column(length = 60, nullable = false)
	private String senha;


	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Post> posts;

	@OneToMany(mappedBy = "id.userSeguidor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Relationship> seguidores;
	
	@OneToMany(mappedBy = "id.userSeguido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Relationship> seguindo;

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

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Relationship> getSeguidores() {
		return seguidores;
	}

	public void setSeguidores(List<Relationship> seguidores) {
		this.seguidores = seguidores;
	}

	public List<Relationship> getSeguindo() {
		return seguindo;
	}

	public void setSeguindo(List<Relationship> seguindo) {
		this.seguindo = seguindo;
	}

	
}
