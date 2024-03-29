package br.com.serragram.serragram.service;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.serragram.serragram.DTO.UserAlterarSenhaDTO;
import br.com.serragram.serragram.DTO.UserAtualizarDTO;
import br.com.serragram.serragram.DTO.UserDTO;
import br.com.serragram.serragram.DTO.UserInserirDTO;
import br.com.serragram.serragram.config.MailConfig;
import br.com.serragram.serragram.exceptions.UnprocessableEntityException;
import br.com.serragram.serragram.model.User;
import br.com.serragram.serragram.repository.UserRepository;
import br.com.serragram.serragram.utils.Util;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private MailConfig mailConfig;

	@Autowired
	private FotoService fotoService;

	// GetAll
	public List<UserDTO> findAll() {
		List<User> users = userRepository.findAll();
		List<UserDTO> usersDTO = users.stream().map(usuario -> {
			return adicionaImagemURI(usuario);
		}).collect(Collectors.toList());

		return usersDTO;
	}

	// GetId
	public UserDTO findById(Long id) throws UnprocessableEntityException {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			throw new UnprocessableEntityException("Usuário inexistente!!");
		}
		return adicionaImagemURI(userOpt.get());
	}

	// Post
	@Transactional
	public UserDTO inserir(UserInserirDTO userInserirDTO, MultipartFile file)
			throws UnprocessableEntityException, IOException {
		if (!userInserirDTO.getSenha().equalsIgnoreCase(userInserirDTO.getConfirmaSenha())) {
			throw new UnprocessableEntityException("Senha e confirma senha devem ser idênticas.");
		}
		
		User userNomeCompletoExistente = userRepository.findByNomeAndSobreNome(userInserirDTO.getNome(), userInserirDTO.getSobreNome());
		
		if(userNomeCompletoExistente != null) {
			throw new UnprocessableEntityException("Nome e sobrenome já cadastrados");
		}

		User userEmailExistente = userRepository.findByEmail(userInserirDTO.getEmail());
		if (userEmailExistente != null) {
			throw new UnprocessableEntityException("E-mail já cadastrado.");
		}

		User user = new User();
		user.setNome(userInserirDTO.getNome());
		user.setSobreNome(userInserirDTO.getSobreNome());
		user.setDataNascimento(userInserirDTO.getDataNascimento());
		user.setEmail(userInserirDTO.getEmail());
		user.setSenha(bCryptPasswordEncoder.encode(userInserirDTO.getSenha()));

		user = userRepository.save(user);
		fotoService.inserir(user, file);

		mailConfig.sendEmail(user.getEmail(), "Cadastro Realizado!", user.toString());
		return adicionaImagemURI(user);
	}

	// PutSenha
	@Transactional
	public UserInserirDTO atualizarSenha(UserAlterarSenhaDTO userAlterarSenhaDTO, Long id)
			throws UnprocessableEntityException {
		if (!userAlterarSenhaDTO.getNovaSenha().equalsIgnoreCase(userAlterarSenhaDTO.getConfirmaNovaSenha())) {
			throw new UnprocessableEntityException("Senha e confirma senha devem ser idênticas.");
		}
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			throw new UnprocessableEntityException("Id não existente");
		}
		User user = userOpt.get();
		user.setId(id);
		user.setSenha(bCryptPasswordEncoder.encode(userAlterarSenhaDTO.getNovaSenha()));
		userRepository.save(user);
		UserInserirDTO userInserirDTO = new UserInserirDTO(user, userAlterarSenhaDTO);
		return userInserirDTO;
	}

	// Put
	@Transactional
	public UserAtualizarDTO atualizar(UserAtualizarDTO userAtualizarDTO, Long id) throws UnprocessableEntityException {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			throw new UnprocessableEntityException("Id não existente");
		}
		User user = userOpt.get();
		
		User userNomeCompletoExistente = userRepository.findByNomeAndSobreNome(userAtualizarDTO.getNome(), userAtualizarDTO.getSobreNome());			
		
		if(userAtualizarDTO.getNome() != null || userAtualizarDTO.getSobreNome() != null) {
			
			if(userAtualizarDTO.getNome() != null && userAtualizarDTO.getSobreNome() != null && userNomeCompletoExistente != null) {
				throw new UnprocessableEntityException("Nome e sobrenome já cadastrados");
			}

			else if (userAtualizarDTO.getSobreNome() == null && userRepository.findByNomeAndSobreNome(userAtualizarDTO.getNome(), user.getSobreNome()) != null) {
				throw new UnprocessableEntityException("Nome e sobrenome já cadastrados");
			}
			
			else if (userAtualizarDTO.getNome() == null && userRepository.findByNomeAndSobreNome(user.getNome(), userAtualizarDTO.getSobreNome()) != null) {
				throw new UnprocessableEntityException("Nome e sobrenome já cadastrados");
			}
			
		}
		User userEmailExistente = userRepository.findByEmail(userAtualizarDTO.getEmail());
		if (userEmailExistente != null) {
			throw new UnprocessableEntityException("E-mail já cadastrado.");
		}	
		
		user.setId(id);
		// ele faz a copia dos atributos não nulos de user para userInserirDTO
		Util.copyNonNullProperties(userAtualizarDTO, user);		
		
		userRepository.save(user);
		return new UserAtualizarDTO(user);
	}

	// Delete
	@Transactional
	public void remover(Long id) throws UnprocessableEntityException {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			throw new UnprocessableEntityException("Não existe este id."); // lançando exceção default
		}
		userRepository.deleteById(id);
	}

	public UserDTO adicionaImagemURI(User user) {
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{id}/foto")
				.buildAndExpand(user.getId()).toUri();

		UserDTO dto = new UserDTO(user);
		dto.setUrl(uri.toString());
		return dto;
	}
}