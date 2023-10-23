package br.com.serragram.serragram.service;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	/*
	 * @Autowired private RelationshipRepository relationshipRepository;
	 */

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

//	// GetNativo
//	public Page<UserDTO> buscarDataNascimento(Date dataMinima, Date dataMaxima, Pageable Pageable) {
//		Page<UserDTO> buscarDataNativo = userRepository.buscarDataNativo(dataMinima, dataMaxima, Pageable);
//		if(buscarDataNativo.isEmpty()) {
//			throw new UnprocessableEntityException("Não existe usuario nessa faixa de Data");
//		}		
//		return buscarDataNativo;
//	}

	// Post
	@Transactional
	public UserDTO inserir(UserInserirDTO userInserirDTO, MultipartFile file)
			throws UnprocessableEntityException, IOException {
		if (!userInserirDTO.getSenha().equalsIgnoreCase(userInserirDTO.getConfirmaSenha())) {
			throw new UnprocessableEntityException("Senha e confirma senha devem ser idênticas.");
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
		User userEmailExistente = userRepository.findByEmail(userAtualizarDTO.getEmail());
		if (userEmailExistente != null) {
			throw new UnprocessableEntityException("E-mail já cadastrado.");
		}
		User user = userOpt.get();
//		String senha = user.getSenha();
//		if (userInserirDTO.getSenha() != null && !userInserirDTO.getSenha().equals(senha)) {
//			throw new UnprocessableEntityException(
//					"Não é possível atualizar senha neste endpoint.\nFavor utiizar o endpoint /users/senha/id");
//		}
		user.setId(id);
		Util.copyNonNullProperties(userAtualizarDTO, user);
		// ele faz a copia dos atributos não nulos de user para userInserirDTO
//		user.setSenha(senha);
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

//	=====================================================

	public UserDTO adicionaImagemURI(User user) {
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{id}/foto")
				.buildAndExpand(user.getId()).toUri();

		UserDTO dto = new UserDTO(user);
		dto.setUrl(uri.toString());
		return dto;
	}
}