package br.com.magnasistemas.api_saude.validators.implementers.especialidade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.dto.especialidade.DadosAtualizarEspecialidade;
import br.com.magnasistemas.api_saude.repository.EspecialidadeRepository;
import br.com.magnasistemas.api_saude.validators.interfaces.especialidade.AtualizarEspecialidadeValidator;
import jakarta.persistence.EntityNotFoundException;

@Component
public class ValidadorEspecialidadeAtualizacao implements AtualizarEspecialidadeValidator {

	@Autowired
	EspecialidadeRepository especialidadeRepository;
	
	@Override
	public void validador(DadosAtualizarEspecialidade dados) {
		if(!especialidadeRepository.existsById(dados.id())) {
			throw new EntityNotFoundException();
		}

	}

}
