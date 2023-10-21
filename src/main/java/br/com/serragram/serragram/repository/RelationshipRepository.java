package br.com.serragram.serragram.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.serragram.serragram.model.Relationship;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

	//@Query(value = "SELECT * FROM relationship WHERE id_user_seguidor = :seguidorId AND id_user_seguido = :seguidoId", nativeQuery = true)
	//Optional<Relationship> findRelationship(Long seguidorId, Long seguidoId);
	
	Optional<Relationship> findByIdUserSeguidorIdAndIdUserSeguidoId(Long seguidorId, Long seguidoId);
	
	//@Query(value = "DELETE FROM relationship WHERE id_user_seguidor = :seguidorId AND id_user_seguido = :seguidoId", nativeQuery = true)
	//Optional<Relationship> deleteRelationship(Long seguidorId, Long seguidoId);
	
	void deleteByIdUserSeguidorIdAndIdUserSeguidoId(Long seguidorId, Long seguidoId);
}
