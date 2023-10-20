package br.com.serragram.serragram.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import br.com.serragram.serragram.model.Comment;
import br.com.serragram.serragram.model.Post;
import br.com.serragram.serragram.model.User;

public class PostDTO {
	
	private Long id;
	
	private String conteudo;	
	
	private Calendar dataCricao;
	
	private User autor;
	
	//private List<Comment> comentarios;
	
	public PostDTO() {}
	
	public PostDTO(Post post) {
		this.id = post.getId();
		this.conteudo = post.getConteudo();
		this.dataCricao = post.getDataCriaçao();
		this.autor = post.getAutor();
		/*this.comentarios = new ArrayList<>();
		for (Comment comments : post.getComentarios()) {
			if (comments.getPost().getId() == post.getId()) {
				comentarios.add(comments);
			}
		}*/
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public Calendar getDataCricao() {
		return dataCricao;
	}
	
	public void setDataCricao(Calendar dataCricao) {
		this.dataCricao = dataCricao;
	}

	public User getAutor() {
		return autor;
	}

	public void setAutor(User autor) {
		this.autor = autor;
	}


	

	

}
