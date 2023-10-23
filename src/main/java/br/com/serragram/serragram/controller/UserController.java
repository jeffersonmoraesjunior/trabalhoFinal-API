package br.com.serragram.serragram.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.serragram.serragram.DTO.UserAlterarSenhaDTO;
import br.com.serragram.serragram.DTO.UserAtualizarDTO;
import br.com.serragram.serragram.DTO.UserDTO;
import br.com.serragram.serragram.DTO.UserInserirDTO;
import br.com.serragram.serragram.exceptions.UnprocessableEntityException;
import br.com.serragram.serragram.model.Foto;
import br.com.serragram.serragram.service.FotoService;
import br.com.serragram.serragram.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;


@RestController
@Api(value = "users")
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FotoService fotoService;
	
	@GetMapping
	@ApiOperation(value = "Retorna lista de usuários", notes = "Lista de usuários:")
	public ResponseEntity<List<UserDTO>> listar() {
		return ResponseEntity.ok(userService.findAll());
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Retorna usuário por id", notes = "Usuário buscado {id}:")
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
	
	@GetMapping("/{id}/foto")
	public ResponseEntity<byte[]> buscarFoto(@PathVariable Long id) {
		Foto foto = fotoService.buscarFotoPorIdUser(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, foto.getTipo());
		headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(foto.getDados().length));
		return new ResponseEntity<>(foto.getDados(), headers, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Cria cadastro de usuário", notes = "Criando usuário:")
//	@ApiImplicitParams({
//	    @ApiImplicitParam(name = "userInserirDTO", dataType = "UserInserirDTO", paramType = "body")
//	})
//	@RequestMapping(value = "/cadastro", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)	
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

	@ApiOperation(value = "Altera senha de usuário por id", notes = "Alterando senha de usuário id:")
	@PutMapping("/senha/{id}")
	public ResponseEntity<UserInserirDTO> atualizarSenha(@Valid @RequestBody UserAlterarSenhaDTO userAlterarSenhaDTO, @PathVariable Long id) {
		UserInserirDTO userInserirDTO = userService.atualizarSenha(userAlterarSenhaDTO, id);
		if (userInserirDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userInserirDTO);
	}
	
	@ApiOperation(value = "Altera os dados do usuário por id", notes = "Alterando dados do usuário por id:")
	@PutMapping("/{id}")
	public ResponseEntity<UserAtualizarDTO> atualizar(@Valid @RequestBody UserAtualizarDTO userAtualizarDTO, @PathVariable Long id) {
		UserAtualizarDTO userDTO = userService.atualizar(userAtualizarDTO, id);
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
	
	@ApiOperation(value = "Deleta usuário por id", notes = "Deletando usuário por id")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		userService.remover(id);
		return ResponseEntity.noContent().build();
	}
	
}
