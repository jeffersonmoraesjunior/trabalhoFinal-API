package br.com.serragram.serragram.service;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serragram.serragram.DTO.PostDTO;
import br.com.serragram.serragram.DTO.PostInserirDTO;
import br.com.serragram.serragram.config.MailConfig;
import br.com.serragram.serragram.exceptions.PostException;
import br.com.serragram.serragram.model.Post;
import br.com.serragram.serragram.model.User;
import br.com.serragram.serragram.repository.PostRepository;
import br.com.serragram.serragram.repository.UserRepository;
import br.com.serragram.serragram.utils.Util;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MailConfig mailConfig;
	
	//GetAll
	public List<PostDTO> findAll(){
		List<Post> posts = postRepository.findAll();
		List<PostDTO> postDTO = posts.stream().map(post -> new PostDTO(post)).collect(Collectors.toList());
		return postDTO;
	}
	
	//Get ID
	public PostDTO findById(Long id) throws PostException {
		Optional<Post> postOpt = postRepository.findById(id);
		if(postOpt.isEmpty()) {
			throw new PostException("Post Inexistente");
		}
		PostDTO postDTO = new PostDTO(postOpt.get());
		return postDTO;
	}

	//Post
	@Transactional
	public PostDTO inserir(PostInserirDTO postInserirDTO) throws PostException {
		if(postInserirDTO.getConteudo().isEmpty()) {
			throw new PostException("Insira um conteúdo.");
		}
		
		Post post = new Post();
		post.setConteudo(postInserirDTO.getConteudo());
		post.setDataCriacao(Calendar.getInstance());		
		
		Optional<User> userOpt = userRepository.findById(postInserirDTO.getAutorId());
		if(userOpt.isEmpty()) {
			throw new PostException("Autor não encontrado."); 
		}
		
		post.setAutor(userOpt.get());
		post = postRepository.save(post);
		PostDTO postDTO = new PostDTO(post);
		String email = userOpt.get().getEmail();
		mailConfig.sendEmail(email, "Você fez um Novo Post...", post.toString());
		return postDTO;
		
	}
	
	//Put
	public PostDTO atualizar(PostInserirDTO postInserirDTO, Long id) throws PostException {
		Optional<Post> postOpt = postRepository.findById(id);
		if(postOpt.isEmpty()) {
			throw new PostException("Post não encontrado, verifique novamente.");
		}
		Post post = postOpt.get();
		post.setId(id);
		Util.copyNonNullProperties(postInserirDTO, post);
		postRepository.save(post);
		PostDTO postDTO = new PostDTO(post);
		return postDTO;
	}
	
	//Delete
	public void remover(Long id) throws PostException {
		Optional<Post> postOpt = postRepository.findById(id);
		if(postOpt.isEmpty()) {
			throw new PostException("Post não encontrado, verifique novamente.");
		}
		postRepository.deleteById(id);
	}
	
}














