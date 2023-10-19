package br.com.serragram.serragram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.serragram.serragram.model.Comment;

@Repository 
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
