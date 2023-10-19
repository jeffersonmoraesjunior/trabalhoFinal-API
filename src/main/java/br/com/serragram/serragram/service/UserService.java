package br.com.serragram.serragram.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.serragram.serragram.DTO.UserDTO;
import br.com.serragram.serragram.DTO.UserInserirDTO;
import br.com.serragram.serragram.model.User;
import br.com.serragram.serragram.repository.RelationshipRepository;
import br.com.serragram.serragram.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RelationshipRepository relationshipRepository;
	
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
	public UserDTO inserir (UserInserirDTO userInserirDTO) {
		if (!userInserirDTO.getSenha().equalsIgnoreCase(userInserirDTO.getConfirmaSenha())) {
			return null;
		}
		
		User userEmailExistente = userRepository.findByEmail(userInserirDTO.getEmail());
		if (userEmailExistente != null) {
			return null;
		}
		
		User user = new User();
		user.setNome(userInserirDTO.getNome());
		user.setEmail(userInserirDTO.getEmail());
		//user.setSenha(bCryptPasswordEncoder.encode(userInserirDTO.getSenha()));
		
		user = userRepository.save(user);
		UserDTO userDTO = new UserDTO(user);
		return userDTO;
	}
	
	// Put
	public UserDTO atualizar(User user, Long id) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			return null;
		}
		user.setId(id);
		userRepository.save(user);
		UserDTO userDTO = new UserDTO(user);
		return userDTO;
	}
	
	// Delete
	public void remover(Long id) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			 
		}
		userRepository.deleteById(id);
	}
}
