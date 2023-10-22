package br.com.serragram.serragram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.serragram.serragram.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
	
//	@Query(value = "select new br.com.serragram.serragram.model.UserDTO(id, nome, sobreNome, email, dataNascimento, post) from user u u.data_nascimento >= :dataMinima and u.data_nascimento <= :dataMaxima", nativeQuery = true)
//	Page<UserDTO> buscarDataNativo(Date dataMinima, Date dataMaxima, Pageable Pageable);
}
