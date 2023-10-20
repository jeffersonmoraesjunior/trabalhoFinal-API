package br.com.serragram.serragram.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
	@Autowired
	private RelationshipRepository relationshipRepository;
	*/
	
	// GetAll
	public List<UserDTO> findAll() {
		List<User> users = userRepository.findAll();
		List<UserDTO> usersDTO = users.stream().map(usuario -> new UserDTO(usuario))
				.collect(Collectors.toList());
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
	public UserDTO inserir (UserInserirDTO userInserirDTO) throws UserException {
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
	
	// Put
<<<<<<< Updated upstream
	public UserDTO atualizar(UserInserirDTO userInserirDTO, Long id) throws UserException {
=======
	public UserDTO atualizar(UserDTO userDTO, Long id) throws UserException {
>>>>>>> Stashed changes
		Optional<User> userOpt = userRepository.findById(id);
		if(userOpt.isEmpty()) {
			throw new UserException("Id não existente");
		}
		User user = userOpt.get();
		user.setId(id);
<<<<<<< Updated upstream
		user.setNome(userInserirDTO.getNome());
		user.setSobreNome(userInserirDTO.getSobreNome());
		user.setDataNascimento(userInserirDTO.getDataNascimento());
		user.setEmail(userInserirDTO.getEmail());
		user.setSenha(bCryptPasswordEncoder.encode(userInserirDTO.getSenha()));
=======
		//ele faz a copia dos atributos nulos de userDTO para user
		Util.copyNonNullProperties(userDTO, user);
>>>>>>> Stashed changes
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