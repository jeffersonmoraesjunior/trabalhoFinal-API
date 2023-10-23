package br.com.serragram.serragram.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.serragram.serragram.DTO.RelationshipDTO;
import br.com.serragram.serragram.DTO.RelationshipInserirDTO;
import br.com.serragram.serragram.DTO.UserDTO;
import br.com.serragram.serragram.DTO.UserRelationshipDTO;
import br.com.serragram.serragram.model.Relationship;
import br.com.serragram.serragram.service.RelationshipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "relationship")
@RequestMapping("/relationships")
public class RelationshipController {

	@Autowired
	private RelationshipService relationshipService;
	
	@GetMapping
	@ApiOperation(value = "Retorna lista de relacionamento", notes = "Lista de relacionamento:")
	public ResponseEntity<List<RelationshipDTO>> listar(){
		return ResponseEntity.ok(relationshipService.findAll());
	}
	
	@GetMapping("/followers/{id}")
	@ApiOperation(value = "Retorna lista de relacionamento por id", notes = "Lista de relacionamento por id:")
	public ResponseEntity<Page<UserRelationshipDTO>> findByIdUserSeguidoId(@PathVariable Long id, Pageable pageable){
		Page<UserRelationshipDTO> seguidores = relationshipService.buscarSeguidores(id, pageable);		
		return ResponseEntity.ok(seguidores);
	}
	
	@PostMapping
	@ApiOperation(value = "Cria relacionamento", notes = "Seguir:")
	public ResponseEntity<RelationshipDTO> inserir(@Valid @RequestBody RelationshipInserirDTO relationshipInserirDTO) {
		RelationshipDTO relationshipDTO = relationshipService.inserir(relationshipInserirDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(relationshipDTO);
	}
	
	@DeleteMapping("/unfollow")
	@ApiOperation(value = "Deleta relacionamento", notes = "Deixa de seguir:")
	public ResponseEntity<Void> remover(@RequestParam Long seguidorId, @RequestParam Long seguidoId) {
		relationshipService.remover(seguidorId, seguidoId);
		return ResponseEntity.noContent().build();
	}
	
	
}
