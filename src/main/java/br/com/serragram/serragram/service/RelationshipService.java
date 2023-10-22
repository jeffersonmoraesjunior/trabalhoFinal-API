package br.com.serragram.serragram.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.serragram.serragram.DTO.RelationshipDTO;
import br.com.serragram.serragram.DTO.RelationshipInserirDTO;
import br.com.serragram.serragram.config.MailConfig;
import br.com.serragram.serragram.exceptions.UnprocessableEntityException;
import br.com.serragram.serragram.model.Relationship;
import br.com.serragram.serragram.model.User;
import br.com.serragram.serragram.repository.RelationshipRepository;
import br.com.serragram.serragram.repository.UserRepository;

@Service
public class RelationshipService {

	@Autowired
	private RelationshipRepository relationshipRepository;

	@Autowired
	private MailConfig mailConfig;

	@Autowired
	private UserRepository userRepository;

	public List<RelationshipDTO> findAll() {
		List<Relationship> relationships = relationshipRepository.findAll();
		List<RelationshipDTO> relationshipDTO = relationships.stream()
				.map(relationship -> new RelationshipDTO(relationship)).collect(Collectors.toList());
		return relationshipDTO;
	}
	
	public Page<Relationship> buscarSeguidores(Long id, Pageable pageable){
		Page<Relationship> seguidores = relationshipRepository.findByIdUserSeguidoId(id, pageable);		
		if (seguidores.isEmpty()) {
			throw new UnprocessableEntityException("Este usuário não tem seguidores");
		}
		return seguidores;		
	}

	// Post
	@Transactional
	public RelationshipDTO inserir(RelationshipInserirDTO relationshipInserirDTO) throws UnprocessableEntityException {
		Relationship relationship = new Relationship();
		Optional<User> seguidorOpt = userRepository.findById(relationshipInserirDTO.getSeguidorId());
		Optional<User> seguidoOpt = userRepository.findById(relationshipInserirDTO.getSeguidoId());
		if (relationshipInserirDTO.getSeguidorId() == relationshipInserirDTO.getSeguidoId()) {
			throw new UnprocessableEntityException("Mesmo ID, favor informar IDs diferentes.");
		}
		if (seguidorOpt.isEmpty()) {
			throw new UnprocessableEntityException("seguidorId não encontrado, verifique novamente.");
		}
		if (seguidoOpt.isEmpty()) {
			throw new UnprocessableEntityException("seguidoId não encontrado, verifique novamente.");
		}
		relationship.getId().setUserSeguidor(seguidorOpt.get());
		relationship.getId().setUserSeguido(seguidoOpt.get());
		relationship.setDataInicioSeguimento(Calendar.getInstance().getTime());
		relationship = relationshipRepository.save(relationship);
		RelationshipDTO relationshipDTO = new RelationshipDTO(relationship);
		String email = relationship.getId().getUserSeguido().getEmail();
		mailConfig.sendEmail(email, "Você tem um novo seguidor...",
				relationship.getId().getUserSeguidor().getNome() + " seguiu você!");
		return relationshipDTO;

	}

	// Delete
	@Transactional
	public void remover(Long seguidorId, Long seguidoId) throws UnprocessableEntityException {
		Optional<Relationship> relationshipOpt = relationshipRepository.findByIdUserSeguidorIdAndIdUserSeguidoId(seguidorId, seguidoId);
		if(relationshipOpt.isEmpty()) {
			throw new UnprocessableEntityException("Relacionamento não encontrado, verifique novamente.");
		}
		
		relationshipRepository.deleteByIdUserSeguidorIdAndIdUserSeguidoId(seguidorId, seguidoId);
		/*EntityManager entityManager;// Obtenha o EntityManager de sua unidade de persistência
		RelationshipPK chaveParaExcluir = new RelationshipPK(seguidorId, seguidoId); // Substitua id1 e id2 pelos valores reais

		Relationship relacionamentoParaExcluir = entityManager.find(Relationship.class, chaveParaExcluir);

				if (relacionamentoParaExcluir != null) {
				    entityManager.getTransaction().begin();
				    entityManager.remove(relacionamentoParaExcluir);
				    entityManager.getTransaction().commit();
				}*/

	}
}
