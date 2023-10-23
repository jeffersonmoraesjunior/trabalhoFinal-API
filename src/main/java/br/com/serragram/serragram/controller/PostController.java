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
import br.com.serragram.serragram.DTO.PostEditarDTO;
import br.com.serragram.serragram.DTO.PostInserirDTO;
import br.com.serragram.serragram.DTO.SomaPostDTO;
import br.com.serragram.serragram.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "posts")
@RequestMapping("/posts")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@GetMapping
	@ApiOperation(value = "Retorna lista de posts", notes = "Listando comentário:")
	public ResponseEntity<List<PostDTO>> listar(){
		//UserDetails detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(postService.findAll());
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Retorna post por id", notes = "Busca de post por id:")
	public ResponseEntity<PostDTO> buscar(@PathVariable Long id) {
		PostDTO postDTO = postService.findById(id);
		if(postDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(postDTO);
	}
	
	//SOMA POST
	@GetMapping("/soma/{id}")
	@ApiOperation(value = "Retorna total de posts e comentários do usuario", notes = "Total de posts e comentários do usuario: ")
	public ResponseEntity<SomaPostDTO> somaPost(@PathVariable Long id) {
		SomaPostDTO postDTO = postService.somaPost(id);		
		return ResponseEntity.ok(postDTO);
	}
	
	@PostMapping
	@ApiOperation(value = "Cria post", notes = "Adicionando post:")
	public ResponseEntity<PostDTO> inserir( @Valid @RequestBody PostInserirDTO postInserirDTO) {
		PostDTO postDTO = postService.inserir(postInserirDTO);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(postDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(postDTO);
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Altera post", notes = "Alterando post:")
	public ResponseEntity<PostDTO> atualizar(@Valid @RequestBody PostEditarDTO postEditarDTO, @PathVariable Long id) {
		PostDTO postDTO = postService.atualizar(postEditarDTO, id);
		if(postDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(postDTO);

	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deleta post", notes = "Deletando Post:")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		postService.remover(id);
		return ResponseEntity.noContent().build();
	}
	
}
