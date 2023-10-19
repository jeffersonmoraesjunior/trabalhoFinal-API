package br.com.serragram.serragram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.serragram.serragram.model.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
}
