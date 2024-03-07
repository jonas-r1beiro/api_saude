package br.com.magnasistemas.api_saude.validators.implementers.paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.repository.PacienteRepository;
import br.com.magnasistemas.api_saude.validators.interfaces.paciente.ExistenciaPacientePorIdValidator;
import jakarta.persistence.EntityNotFoundException;

@Component
public class ValidadorPacienteDelete implements ExistenciaPacientePorIdValidator {

	@Autowired
	PacienteRepository pacienteRepository;
	
	@Override
	public void validador(Long id) {
		if(!pacienteRepository.existsById(id)) {
			throw new EntityNotFoundException();
		}
		
	}
	
}
