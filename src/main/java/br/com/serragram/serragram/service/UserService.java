package br.com.serragram.serragram.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.serragram.serragram.DTO.UserAlterarSenhaDTO;
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

	/*
	 * @Autowired private RelationshipRepository relationshipRepository;
	 */

	// GetAll
	public List<UserDTO> findAll() {
		List<User> users = userRepository.findAll();
		List<UserDTO> usersDTO = users.stream().map(usuario -> new UserDTO(usuario)).collect(Collectors.toList());
		return usersDTO;
	}

	// GetId
	public UserDTO findById(Long id) throws UnprocessableEntityException {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			throw new UnprocessableEntityException("Usuário inexistente!!");
		}
		UserDTO userDTO = new UserDTO(userOpt.get());
		return userDTO;
	}

	// Post
	@Transactional
	public UserDTO inserir(UserInserirDTO userInserirDTO) throws UnprocessableEntityException {
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
		user.setEmail(userInserirDTO.getEmail());
		user.setDataNascimento(userInserirDTO.getDataNascimento());
		user.setSenha(bCryptPasswordEncoder.encode(userInserirDTO.getSenha()));

		user = userRepository.save(user);
		mailConfig.sendEmail(user.getEmail(), "Cadastro Realizado!", user.toString());
		UserDTO userDTO = new UserDTO(user);
		return userDTO;
	}

	// PutSenha
	@Transactional
	public UserInserirDTO atualizarSenha(UserAlterarSenhaDTO userAlterarSenhaDTO, Long id) throws UnprocessableEntityException {
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
	public UserDTO atualizar(UserInserirDTO userInserirDTO, Long id) throws UnprocessableEntityException {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			throw new UnprocessableEntityException("Id não existente");
		}
		User user = userOpt.get();
		String senha = user.getSenha();
		if(userInserirDTO.getSenha() != null && !userInserirDTO.getSenha().equals(senha)) {
			throw new UnprocessableEntityException("Não é possível atualizar senha neste endpoint.\nFavor utiizar o endpoint /users/senha/id");
		}
		user.setId(id);
		Util.copyNonNullProperties(userInserirDTO, user);
		// ele faz a copia dos atributos não nulos de user para userDTO
		user.setSenha(senha);
		userRepository.save(user);
		return new UserDTO(user);
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
}