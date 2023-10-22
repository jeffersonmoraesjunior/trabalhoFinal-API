package br.com.serragram.serragram.controller;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.serragram.serragram.DTO.UserAlterarSenhaDTO;
import br.com.serragram.serragram.DTO.UserDTO;
import br.com.serragram.serragram.DTO.UserInserirDTO;
import br.com.serragram.serragram.exceptions.UnprocessableEntityException;
import br.com.serragram.serragram.model.User;
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
	
//	@GetMapping
//	public ResponseEntity<Page<UserDTO>> buscarDataNascimento(@RequestParam(value="dataMinima") @DateTimeFormat(pattern="MMddyyyy") Date dataMinima,@RequestParam(value="dataMaxima")  @DateTimeFormat(pattern="MMddyyyy") Date dataMaxima, Pageable pageable){
//		System.out.println("PASSOU CONTROLLER");
//		Page<UserDTO> pageUserDTO = userService.buscarDataNascimento(dataMinima, dataMaxima, pageable);
//		return ResponseEntity.ok(pageUserDTO);
//	}

	@PostMapping(value = "/cadastro", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<UserDTO> inserir(@Valid @RequestPart UserInserirDTO userInserirDTO, @RequestPart MultipartFile file) throws UnprocessableEntityException, IOException {
		UserDTO userDTO = userService.inserir(userInserirDTO, file);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(userDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(userDTO);
	}


	@PutMapping("/senha/{id}")
	public ResponseEntity<UserInserirDTO> atualizarSenha(@Valid @RequestBody UserAlterarSenhaDTO userAlterarSenhaDTO, @PathVariable Long id) {
		UserInserirDTO userInserirDTO = userService.atualizarSenha(userAlterarSenhaDTO, id);
		if (userInserirDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userInserirDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> atualizar(@Valid @RequestBody UserInserirDTO userInserirDTO, @PathVariable Long id) {
		UserDTO userDTO = userService.atualizar(userInserirDTO, id);
		if(userDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userDTO);
		
		/*try {
			UserDTO updateUser = userService.atualizar(userInserirDTO, id);
			return ResponseEntity.ok(updateUser);
		} catch (UnprocessableEntityException e) {
			throw new UnprocessableEntityException("Erro ao atualizar o user{id}");
		}*/
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		userService.remover(id);
		return ResponseEntity.noContent().build();
	}
	
}
