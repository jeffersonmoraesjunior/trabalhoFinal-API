package br.com.serragram.serragram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.serragram.serragram.DTO.SomaPostDTO;
import br.com.serragram.serragram.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	@Query(value = "select u.id_user as userId, u.nome as nome, u.sobre_nome as sobreNome, "
			+ "(select count(*) from post p where p.user_id = u.id_user) as totalPosts, "
			+ "(select count(*) from comment c where c.user_id = u.id_user) as totalComments "
			+ "from usuario as u "
			+ "where u.id_user = :idUsuario ", nativeQuery = true)	
	SomaPostDTO somaPost(Long idUsuario);
	
}
