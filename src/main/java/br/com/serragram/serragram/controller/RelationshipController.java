package br.com.serragram.serragram.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import br.com.serragram.serragram.DTO.RelationshipDTO;
import br.com.serragram.serragram.DTO.RelationshipInserirDTO;
import br.com.serragram.serragram.service.RelationshipService;

@RestController
@RequestMapping("/relationships")
public class RelationshipController {

	@Autowired
	private RelationshipService relationshipService;
	
	@GetMapping
	public ResponseEntity<List<RelationshipDTO>> listar(){
		return ResponseEntity.ok(relationshipService.findAll());
	}
	
	@PostMapping
	public ResponseEntity<RelationshipDTO> inserir(@Valid @RequestBody RelationshipInserirDTO relationshipInserirDTO) {
		RelationshipDTO relationshipDTO = relationshipService.inserir(relationshipInserirDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(relationshipDTO);
	}
	
	@DeleteMapping("/unfollow")
	public ResponseEntity<Void> remover(@RequestParam Long seguidorId, @RequestParam Long seguidoId) {
		relationshipService.remover(seguidorId, seguidoId);
		return ResponseEntity.noContent().build();
	}
	
	
}
