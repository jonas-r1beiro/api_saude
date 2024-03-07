package br.com.magnasistemas.api_saude.validators.implementers.especialidade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.repository.EspecialidadeRepository;
import br.com.magnasistemas.api_saude.validators.interfaces.especialidade.ExistenciaEspecialidadePorIdValidator;
import jakarta.persistence.EntityNotFoundException;

@Component
public class ValidadorEspecialidadeDetalhar implements ExistenciaEspecialidadePorIdValidator {

	@Autowired
	EspecialidadeRepository especialidadeRepository;
	
	@Override
	public void validador(Long id) {
		if(!especialidadeRepository.existsById(id)) {
			throw new EntityNotFoundException();
		}
		
	}

}
