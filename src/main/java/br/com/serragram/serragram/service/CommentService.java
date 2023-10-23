package br.com.serragram.serragram.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serragram.serragram.DTO.CommentDTO;
import br.com.serragram.serragram.DTO.CommentEditarDTO;
import br.com.serragram.serragram.DTO.CommentInserirDTO;
import br.com.serragram.serragram.config.MailConfig;
import br.com.serragram.serragram.exceptions.UnprocessableEntityException;
import br.com.serragram.serragram.model.Comment;
import br.com.serragram.serragram.model.Post;
import br.com.serragram.serragram.model.Relationship;
import br.com.serragram.serragram.model.User;
import br.com.serragram.serragram.repository.CommentRepository;
import br.com.serragram.serragram.repository.PostRepository;
import br.com.serragram.serragram.repository.RelationshipRepository;
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
	
	@Autowired 
	private RelationshipRepository relationshipRepository;

	public List<CommentDTO> findAll() {
		List<Comment> comments = commentRepository.findAll();
		List<CommentDTO> commentDTO = comments.stream().map(comment -> new CommentDTO(comment)).collect(Collectors.toList());
		return commentDTO;
	}

	public CommentDTO findById(Long id) throws UnprocessableEntityException  {
		Optional<Comment> comment = commentRepository.findById(id);
		if (comment.isEmpty()) {
			throw new UnprocessableEntityException("Comentário inexistente!");
		}

		return new CommentDTO(comment.get());
	}

	@Transactional
	public CommentDTO inserir(CommentInserirDTO commentInserirDTO) throws UnprocessableEntityException {
		Comment comment = new Comment();
		comment.setTexto(commentInserirDTO.getTexto());
		comment.setDataCriacao(Calendar.getInstance());
		
		Optional<User> userOpt = userRepository.findById(commentInserirDTO.getAutorId());
		Optional<Post> postOpt = postRepository.findById(commentInserirDTO.getPostId());
		
		if(userOpt.isEmpty()) {
			throw new UnprocessableEntityException("Autor não encontrado."); 
		} else if(postOpt.isEmpty()) {
			throw new UnprocessableEntityException("Post não encontrado.");
		}
				
		comment.setAutorComentario(userOpt.get());
		comment.setPost(postOpt.get());
		
		Long autorComentarioId = commentInserirDTO.getAutorId();
		Long autorPostId = comment.getPost().getAutor().getId();
		
		Optional<Relationship> relationshipOpt = relationshipRepository.findByIdUserSeguidorIdAndIdUserSeguidoId(autorComentarioId, autorPostId);
		if(relationshipOpt.isEmpty()) {
			throw new UnprocessableEntityException("Comentário exclusivo para seguidores.");
		}
		
		comment = commentRepository.save(comment);
		CommentDTO commentDTO = new CommentDTO(comment);
		String email = postOpt.get().getAutor().getEmail();
		mailConfig.sendEmail(email, userOpt.get().getNome() + " fez um comentário no seu post!!!", comment.toString());
		return commentDTO;
		
	}

	public CommentDTO atualizar(CommentEditarDTO commentEditarDTO, Long id) throws UnprocessableEntityException  {
		Optional<Comment> commentOpt = commentRepository.findById(id);
		if (commentOpt.isEmpty()) {
			throw new UnprocessableEntityException("Comentário não encontrado, verifique novamente.");
		}
		Comment comment = commentOpt.get();
		comment.setId(id);
		comment.setTexto(commentEditarDTO.getTexto());
		comment.setDataCriacao(Calendar.getInstance());
		comment = commentRepository.save(comment);
		CommentDTO commentDTO = new CommentDTO(comment);
		return commentDTO;
	}

	@Transactional
	public void remover(Long id) throws UnprocessableEntityException  {
		Optional<Comment> commentOpt = commentRepository.findById(id);
		if (commentOpt.isEmpty()) {
			throw new UnprocessableEntityException("Comentário não encontrado, verifique novamente.");
		}

		commentRepository.delete(commentOpt.get());
	}

}
