package br.com.serragram.serragram.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.serragram.serragram.exceptions.UnprocessableEntityException;
import br.com.serragram.serragram.model.Foto;
import br.com.serragram.serragram.model.User;
import br.com.serragram.serragram.repository.FotoRepository;
import br.com.serragram.serragram.repository.UserRepository;

@Service
public class FotoService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FotoRepository fotoRepository;

	@Transactional
	public Foto inserir(User user, MultipartFile file) throws IOException {
		Foto foto = new Foto();
		foto.setNome(file.getName());
		foto.setTipo(file.getContentType());
		foto.setDados(file.getBytes());
		foto.setUser(user);
		foto = fotoRepository.save(foto);

		return foto;

	}

	@Transactional
	public Foto buscarFotoPorIdUser(Long id) throws UnprocessableEntityException {

		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			throw new UnprocessableEntityException("Usuário não encontrado");
		}

		Optional<Foto> fotoOpt = fotoRepository.findByUser(userOpt.get());
		if (fotoOpt.isEmpty()) {
			throw new UnprocessableEntityException("Foto não encontrada");
		}
		return fotoOpt.get();

	}
}