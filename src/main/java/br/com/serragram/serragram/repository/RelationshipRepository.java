package br.com.serragram.serragram.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.serragram.serragram.model.Relationship;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

	@Query(value = "SELECT * FROM relatonship r WHERE r.id_user_seguidor = :seguidorId AND r.id_user_seguido = :seguidoId", nativeQuery = true)
	Optional<Relationship> findRelationship(Long seguidorId, Long seguidoId);
	
	@Query(value = "DELETE FROM relatonship r WHERE r.id_user_seguidor = :seguidorId AND r.id_user_seguido = :seguidoId", nativeQuery = true)
	Optional<Relationship> deleteRelationship(Long seguidorId, Long seguidoId);
	
}
