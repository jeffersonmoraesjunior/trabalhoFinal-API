package br.com.serragram.serragram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.serragram.serragram.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
}
