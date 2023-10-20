package br.com.serragram.serragram.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.serragram.serragram.DTO.PostDTO;
import br.com.serragram.serragram.model.Post;
import br.com.serragram.serragram.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@GetMapping
	public ResponseEntity<List<PostDTO>> listar(){
		//UserDetails detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(postService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> buscar(@PathVariable Long id) {
		PostDTO postDTO = postService.findById(id);
		if(postDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(postDTO);
	}
	
	@PostMapping
	public ResponseEntity<PostDTO> inserir(@Valid @RequestBody PostDTO postDTO) {
		PostDTO post = postService.inserir(postDTO);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(postDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(post);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> atualizar(@Valid @RequestBody Post post, @PathVariable Long id) {
		PostDTO postDTO = postService.atualizar(post, id);
		if(postDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(postDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		postService.remover(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	
	

}
