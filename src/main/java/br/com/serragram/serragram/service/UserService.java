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
import br.com.serragram.serragram.exceptions.UserException;
import br.com.serragram.serragram.model.User;
import br.com.serragram.serragram.repository.UserRepository;
import br.com.serragram.serragram.utils.Util;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

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
	public UserDTO findById(Long id) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			return null;
		}
		UserDTO userDTO = new UserDTO(userOpt.get());
		return userDTO;
	}

	// Post
	@Transactional
	public UserDTO inserir(UserInserirDTO userInserirDTO) throws UserException {
		if (!userInserirDTO.getSenha().equalsIgnoreCase(userInserirDTO.getConfirmaSenha())) {
			throw new UserException("Senha e confirma senha devem ser idênticas.");
		}

		User userEmailExistente = userRepository.findByEmail(userInserirDTO.getEmail());
		if (userEmailExistente != null) {
			throw new UserException("E-mail já cadastrado.");
		}

		User user = new User();
		user.setNome(userInserirDTO.getNome());
		user.setSobreNome(userInserirDTO.getSobreNome());
		user.setEmail(userInserirDTO.getEmail());
		user.setDataNascimento(userInserirDTO.getDataNascimento());
		user.setSenha(bCryptPasswordEncoder.encode(userInserirDTO.getSenha()));

		user = userRepository.save(user);
		UserDTO userDTO = new UserDTO(user);
		return userDTO;
	}

	// PutSenha
	public UserInserirDTO atualizarSenha(UserAlterarSenhaDTO userAlterarSenhaDTO, Long id) throws UserException {
		if (!userAlterarSenhaDTO.getNovaSenha().equalsIgnoreCase(userAlterarSenhaDTO.getConfirmaNovaSenha())) {
			throw new UserException("Senha e confirma senha devem ser idênticas.");
		}
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			throw new UserException("Id não existente");
		}
		User user = userOpt.get();
		user.setId(id);
		user.setSenha(bCryptPasswordEncoder.encode(userAlterarSenhaDTO.getNovaSenha()));
		userRepository.save(user);
		UserInserirDTO userInserirDTO = new UserInserirDTO(user, userAlterarSenhaDTO);
		return userInserirDTO;
	}

	// Put
	public UserDTO atualizar(UserDTO userDTO, Long id) throws UserException {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			throw new UserException("Id não existente");
		}
		User user = userOpt.get();
		user.setId(id);
		// ele faz a copia dos atributos não nulos de user para userDTO
		Util.copyNonNullProperties(userDTO, user);
		userRepository.save(user);
		return new UserDTO(user);
	}

	// Delete
	public void remover(Long id) throws UserException {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			throw new UserException("Não existe este id."); // lançando exceção default
		}
		userRepository.deleteById(id);
	}
}