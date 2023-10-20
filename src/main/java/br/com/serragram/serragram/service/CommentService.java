package br.com.serragram.serragram.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serragram.serragram.DTO.CommentDTO;
import br.com.serragram.serragram.DTO.CommentInserirDTO;
import br.com.serragram.serragram.DTO.PostDTO;
import br.com.serragram.serragram.config.MailConfig;
import br.com.serragram.serragram.exceptions.PostException;
import br.com.serragram.serragram.model.Comment;
import br.com.serragram.serragram.model.Post;
import br.com.serragram.serragram.model.User;
import br.com.serragram.serragram.repository.CommentRepository;
import br.com.serragram.serragram.repository.PostRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private MailConfig mailConfig; 
	
	public List <Comment>findAll(){
		List<Comment> comments =  commentRepository.findAll();
		return comments;
	}
	
	public CommentDTO findById(Long id) {
		Optional<Comment>  comment =  commentRepository.findById(id);
		if (comment.isEmpty()) {
			return null;
		}
		
		return new CommentDTO(comment.get());	
	}
	
	@Transactional
	public CommentDTO inserir (CommentInserirDTO commentInserirDTO) {
		Comment comment = new Comment();
		comment.setTexto(commentInserirDTO.getTexto());
		comment.setDataCriaçao(Calendar.getInstance());
		Integer cont = 0;
		for (Post post : postRepository.findAll()) {
			if(post.getId() == commentInserirDTO.getPost().getId()) {
				comment.setPost(post);;
				cont++;
				break;
			}
		}
		if (cont == 0) {
			throw new PostException("Post não encontrado.");
		}
		else
		{
		comment = commentRepository.save(comment);
		CommentDTO commentDTO = new CommentDTO(comment);
		mailConfig.sendEmail(commentInserirDTO.getPost().getAutor().getEmail(), "Você tem um novo Comentário...", comment.toString());
		return commentDTO;
		}
	}
	
	public Comment atualizar (Comment comment, Long id) {
		Optional<Comment>  commentOpt=  commentRepository.findById(id);
		if (commentOpt.isEmpty()) {
			return null;
		}
		
		comment.setId(id);
		comment = commentRepository.save(comment);
		return comment;
	}
	
	public void remover(Long id) {
		Optional<Comment>  commentOpt=  commentRepository.findById(id);
		if (commentOpt.isEmpty()) {
			//return null;//
		}
		
		commentRepository.delete(commentOpt.get());
	}
	
}
