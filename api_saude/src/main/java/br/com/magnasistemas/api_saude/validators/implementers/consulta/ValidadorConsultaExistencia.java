package br.com.magnasistemas.api_saude.validators.implementers.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.repository.ConsultaRepository;
import br.com.magnasistemas.api_saude.validators.interfaces.consulta.ExistenciaConsultaPorIdValidator;
import jakarta.persistence.EntityNotFoundException;

@Component
public class ValidadorConsultaExistencia implements ExistenciaConsultaPorIdValidator {
	
	@Autowired
	ConsultaRepository consultaRepository;

	@Override
	public void validador(Long id) {
		if(!consultaRepository.existsById(id)) {
			throw new EntityNotFoundException();
		}

	}

}
