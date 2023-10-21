package br.com.serragram.serragram.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serragram.serragram.DTO.CommentDTO;
import br.com.serragram.serragram.DTO.CommentInserirDTO;
import br.com.serragram.serragram.config.MailConfig;
import br.com.serragram.serragram.exceptions.UnprocessableEntityException;
import br.com.serragram.serragram.model.Comment;
import br.com.serragram.serragram.model.Post;
import br.com.serragram.serragram.model.User;
import br.com.serragram.serragram.repository.CommentRepository;
import br.com.serragram.serragram.repository.PostRepository;
import br.com.serragram.serragram.repository.UserRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private MailConfig mailConfig;
	
	@Autowired
	private UserRepository userRepository;

	public List<Comment> findAll() {
		List<Comment> comments = commentRepository.findAll();
		return comments;
	}

	public CommentDTO findById(Long id) {
		Optional<Comment> comment = commentRepository.findById(id);
		if (comment.isEmpty()) {
			return null;
		}

		return new CommentDTO(comment.get());
	}

	@Transactional
	public CommentDTO inserir(CommentInserirDTO commentInserirDTO) throws UnprocessableEntityException {
		Comment comment = new Comment();
		comment.setTexto(commentInserirDTO.getTexto());
		comment.setDataCriaçao(Calendar.getInstance());
		
		Optional<User> userOpt = userRepository.findById(commentInserirDTO.getAutorId());
		Optional<Post> postOpt = postRepository.findById(commentInserirDTO.getPostId());
		
		if(userOpt.isEmpty()) {
			throw new UnprocessableEntityException("Autor não encontrado."); 
		} else if(postOpt.isEmpty()) {
			throw new UnprocessableEntityException("Post não encontrado.");
		}
				
		comment.setAutorComentario(userOpt.get());
		comment.setPost(postOpt.get());
		
		comment = commentRepository.save(comment);
		CommentDTO commentDTO = new CommentDTO(comment);
		String email = userOpt.get().getEmail();
		mailConfig.sendEmail(email, "Você fez um Novo Post...", comment.toString());
		return commentDTO;
		
	}

	public Comment atualizar(Comment comment, Long id) {
		Optional<Comment> commentOpt = commentRepository.findById(id);
		if (commentOpt.isEmpty()) {
			return null;
		}

		comment.setId(id);
		comment = commentRepository.save(comment);
		return comment;
	}

	public void remover(Long id) {
		Optional<Comment> commentOpt = commentRepository.findById(id);
		if (commentOpt.isEmpty()) {
			// return null;//
		}

		commentRepository.delete(commentOpt.get());
	}

}
