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

import br.com.serragram.serragram.DTO.CommentDTO;
import br.com.serragram.serragram.DTO.CommentInserirDTO;
import br.com.serragram.serragram.exceptions.UnprocessableEntityException;
import br.com.serragram.serragram.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@GetMapping
	public ResponseEntity<List<CommentDTO>> listar() {
		return ResponseEntity.ok(commentService.findAll());

	}

	@GetMapping("/{id}")
	public ResponseEntity<CommentDTO> buscar(@PathVariable Long id) {
		CommentDTO commentDTO = commentService.findById(id);
		if (commentDTO == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(commentDTO);
	}

	@PostMapping
	public ResponseEntity<CommentDTO> inserir(@Valid @RequestBody CommentInserirDTO commentInserirDTO) {
		CommentDTO commentDTO = commentService.inserir(commentInserirDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(commentDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(commentDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CommentDTO> atualizar(@Valid @RequestBody CommentInserirDTO commentInserirDTO, @PathVariable Long id) {
		try {
			CommentDTO updateComment = commentService.atualizar(commentInserirDTO, id);
			return ResponseEntity.ok(updateComment);
		} catch (UnprocessableEntityException e) {
			throw new UnprocessableEntityException("Erro ao atualizar o user{id}");
		}
	}
	
	@DeleteMapping( "/{id}")
	public  ResponseEntity<Void> remover( @PathVariable Long id){
		commentService.remover(id);
		return ResponseEntity.noContent().build();
	}
}
