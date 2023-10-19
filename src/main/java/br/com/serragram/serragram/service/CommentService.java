package br.com.serragram.serragram.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serragram.serragram.model.Comment;
import br.com.serragram.serragram.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	public List <Comment>findAll(){
		List<Comment> comments =  commentRepository.findAll();
		return comments;
	}
	
	public Comment findById(Long id) {
		Optional<Comment>  comment =  commentRepository.findById(id);
		if (comment.isEmpty()) {
			return null;
		}
		
		return comment.get();	
	}
	
	@Transactional
	public Comment inserir (Comment comment) {
		comment = commentRepository.save(comment);
		return comment;
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
