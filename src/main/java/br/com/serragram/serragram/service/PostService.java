package br.com.serragram.serragram.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serragram.serragram.DTO.PostDTO;
import br.com.serragram.serragram.DTO.PostInserirDTO;
import br.com.serragram.serragram.exceptions.PostException;
import br.com.serragram.serragram.model.Post;
import br.com.serragram.serragram.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
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
		post.setDataCriaçao(postInserirDTO.getDataCriacao());
		
		post = postRepository.save(post);
		PostDTO postDTO = new PostDTO (post);
		return postDTO;
	}
	
	//Put
	public PostDTO atualizar(Post post, Long id) throws PostException {
		Optional<Post> postOpt = postRepository.findById(id);
		if(postOpt.isEmpty()) {
			throw new PostException("Post não encontrado, verifique novamente.");
		}
		post.setId(id);
		postRepository.save(post);
		PostDTO postDTO = new PostDTO();
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














