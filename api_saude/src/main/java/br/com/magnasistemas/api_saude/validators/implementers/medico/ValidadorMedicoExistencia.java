package br.com.magnasistemas.api_saude.validators.implementers.medico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.repository.MedicoRepository;
import br.com.magnasistemas.api_saude.validators.interfaces.medico.ExistenciaMedicoPorIdValidator;
import jakarta.persistence.EntityNotFoundException;

@Component
public class ValidadorMedicoExistencia implements ExistenciaMedicoPorIdValidator {

	@Autowired
	MedicoRepository medicoRepository;
	
	@Override
	public void validador(Long id) {
		if(!medicoRepository.existsById(id)) {
			throw new EntityNotFoundException();
		}

	}

}
