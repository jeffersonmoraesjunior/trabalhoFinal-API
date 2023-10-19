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

import br.com.serragram.serragram.model.Comment;
import br.com.serragram.serragram.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@GetMapping
	public ResponseEntity<List<Comment>> listar() {
		return ResponseEntity.ok(commentService.findAll());

	}

	@GetMapping("/{id}")
	public ResponseEntity<Comment> buscar(@PathVariable Long id) {
		Comment comment = commentService.findById(id);
		if (comment == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(comment);
	}

	@PostMapping
	public ResponseEntity<Comment> inserir(@Valid @RequestBody Comment comment) {
		commentService.inserir(comment);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(comment.getId())
				.toUri();
		return ResponseEntity.created(uri).body(comment);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Comment> atualizar(@Valid @RequestBody Comment comment, @PathVariable Long id) {

		if (commentService.atualizar(comment, id) == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(comment);
	}
	
	@DeleteMapping( "/{id}")
	public  ResponseEntity<Void> remover( @PathVariable Long id){
		commentService.remover(id);
		return ResponseEntity.noContent().build();
	}
}
