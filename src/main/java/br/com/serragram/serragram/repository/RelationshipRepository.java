package br.com.serragram.serragram.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.serragram.serragram.DTO.UserRelationshipDTO;
import br.com.serragram.serragram.model.Relationship;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

	Optional<Relationship> findByIdUserSeguidorIdAndIdUserSeguidoId(Long seguidorId, Long seguidoId);

	void deleteByIdUserSeguidorIdAndIdUserSeguidoId(Long seguidorId, Long seguidoId);
	
	 @Query(value = "select r.id_user_seguidor as seguidorId, u.nome as nome, u.sobre_nome as sobreNome, u.email as email, u.data_nascimento as dataNascimento, r.data_inicio_seguimento as dataInicioSeguimento from usuario as u inner join relationship as r on u.id_user = r.id_user_seguidor where r.id_user_seguido = :seguidoId order by r.data_inicio_seguimento", nativeQuery = true)
	 Page<UserRelationshipDTO> findUserDTOById(Long seguidoId, Pageable pageable);
	
}
