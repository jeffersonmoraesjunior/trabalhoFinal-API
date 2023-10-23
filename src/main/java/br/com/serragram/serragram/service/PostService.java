package br.com.serragram.serragram.service;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serragram.serragram.DTO.PostDTO;
import br.com.serragram.serragram.DTO.PostEditarDTO;
import br.com.serragram.serragram.DTO.PostInserirDTO;
import br.com.serragram.serragram.DTO.SomaPostDTO;
import br.com.serragram.serragram.config.MailConfig;
import br.com.serragram.serragram.exceptions.UnprocessableEntityException;
import br.com.serragram.serragram.model.Post;
import br.com.serragram.serragram.model.User;
import br.com.serragram.serragram.repository.PostRepository;
import br.com.serragram.serragram.repository.UserRepository;

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
	public PostDTO findById(Long id) throws UnprocessableEntityException {
		Optional<Post> postOpt = postRepository.findById(id);
		if(postOpt.isEmpty()) {
			throw new UnprocessableEntityException("Post Inexistente!");
		}
		PostDTO postDTO = new PostDTO(postOpt.get());
		return postDTO;
	}
	
	//Get ID - SOMAPOST
	public SomaPostDTO somaPost(Long id) throws UnprocessableEntityException {
		SomaPostDTO postOpt = postRepository.somaPost(id);
		if(postOpt == null) {
			throw new UnprocessableEntityException("Usuario inexistente");
		}
		return postOpt;
	}

	//Post
	@Transactional
	public PostDTO inserir(PostInserirDTO postInserirDTO) throws UnprocessableEntityException {
		if(postInserirDTO.getConteudo().isEmpty()) {
			throw new UnprocessableEntityException("Insira um conteúdo.");
		}
		
		Post post = new Post();
		post.setConteudo(postInserirDTO.getConteudo());
		post.setDataCriacao(Calendar.getInstance());		
		
		Optional<User> userOpt = userRepository.findById(postInserirDTO.getAutorId());
		if(userOpt.isEmpty()) {
			throw new UnprocessableEntityException("Autor não encontrado."); 
		}
		
		post.setAutor(userOpt.get());
		post = postRepository.save(post);
		PostDTO postDTO = new PostDTO(post);
		String email = userOpt.get().getEmail();
		mailConfig.sendEmail(email, "Você fez um Novo Post...", post.toString());
		return postDTO;
		
	}
	
	//Put
	public PostDTO atualizar(PostEditarDTO postEditarDTO, Long id) throws UnprocessableEntityException {
		Optional<Post> postOpt = postRepository.findById(id);
		if(postOpt.isEmpty()) {
			throw new UnprocessableEntityException("Post não encontrado, verifique novamente.");
		}
		Post post = postOpt.get();
		post.setId(id);
		System.out.println(post.getId());
		post.setDataCriacao(Calendar.getInstance());
		postRepository.save(post);
		PostDTO postDTO = new PostDTO(post);
		return postDTO;
	}
	
	//Delete
	@Transactional
	public void remover(Long id) throws UnprocessableEntityException {
		Optional<Post> postOpt = postRepository.findById(id);
		if(postOpt.isEmpty()) {
			throw new UnprocessableEntityException("Post não encontrado, verifique novamente.");
		}
		postRepository.deleteById(id);
	}
	
}














