package br.com.serragram.serragram.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.serragram.serragram.model.Foto;
import br.com.serragram.serragram.model.User;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {

    public Optional<Foto> findByUser(User user);

}
