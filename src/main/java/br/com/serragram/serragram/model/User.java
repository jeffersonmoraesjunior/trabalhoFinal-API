package br.com.serragram.serragram.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "usuario")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	@ApiModelProperty(value = "Identificador unico do usuário")
	private Long id;

	@Column(length = 60, nullable = false)
	@ApiModelProperty(value = "Nome do usuário", required = true)
	private String nome;

	@Column(name = "sobre_nome", length = 60, nullable = false)
	@ApiModelProperty(value = "Sobrenome do usuário", required = true)
	private String sobreNome;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento", nullable = false)
	@JsonFormat(pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Data de nascimento do usuário", required = true)
	private Date dataNascimento;
	
	
	@Column(length = 60, nullable = false)
	@ApiModelProperty(value = "E-mail do usuário", required = true)
	private String email;	
	
	@Column(length = 60, nullable = false)
	@ApiModelProperty(value = "Senha do usuário", required = true)
	private String senha;

	@OneToMany(mappedBy = "autor", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Post> posts;

	@OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private Foto foto;

	public User() {}

	public User(Long id, String nome, String sobreNome,  Date dataNascimento, String email, String senha) {
		this.id = id;
		this.nome = nome;
		this.sobreNome = sobreNome;
		this.dataNascimento = dataNascimento;
		this.email = email;
		this.senha = senha;
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

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return "Nome: " + nome + " " + sobreNome
				+ "\nData de Nascimento: " + sdf.format(dataNascimento)
				+ "\nEmail: " + email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}
	
}
