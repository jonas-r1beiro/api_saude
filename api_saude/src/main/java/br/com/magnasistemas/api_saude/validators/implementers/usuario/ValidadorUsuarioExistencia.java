package br.com.magnasistemas.api_saude.validators.implementers.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.repository.UsuarioRepository;
import br.com.magnasistemas.api_saude.validators.interfaces.usuario.ExistenciaUsuarioValidator;
import jakarta.persistence.EntityNotFoundException;

@Component
public class ValidadorUsuarioExistencia implements ExistenciaUsuarioValidator {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	public void validador(Long id) {
		if(!usuarioRepository.existsById(id)) {
			throw new EntityNotFoundException();
		}

	}

}
