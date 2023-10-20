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

import br.com.serragram.serragram.DTO.UserDTO;
import br.com.serragram.serragram.DTO.UserInserirDTO;
<<<<<<< Updated upstream
=======
import br.com.serragram.serragram.exceptions.UserException;
import br.com.serragram.serragram.model.User;
>>>>>>> Stashed changes
import br.com.serragram.serragram.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> listar() {
		return ResponseEntity.ok(userService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> buscar(@PathVariable Long id) {
		UserDTO userDTO = userService.findById(id);
		if (userDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userDTO);
	}

	@PostMapping
	public ResponseEntity<UserDTO> inserir(@Valid @RequestBody UserInserirDTO userInserirDTO) {
		UserDTO userDTO = userService.inserir(userInserirDTO);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(userDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(userDTO);
	}

<<<<<<< Updated upstream
	@PutMapping("/all/{id}")
	public ResponseEntity<UserDTO> atualizar(@Valid @RequestBody UserInserirDTO userInserirDTO, @PathVariable Long id) {
		UserDTO userDTO = userService.atualizar(userInserirDTO, id);
		if (userDTO == null) {
			return ResponseEntity.notFound().build();
=======
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> atualizar(@Valid @RequestBody UserDTO userDTO, @PathVariable Long id) {
		try {
			UserDTO updateUser = userService.atualizar(userDTO, id);
			return ResponseEntity.ok(updateUser);
		} catch (UserException e) {
			throw new UserException("Erro ao atualizar o user{id}");
>>>>>>> Stashed changes
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		userService.remover(id);
		return ResponseEntity.noContent().build();
	}
	
}
