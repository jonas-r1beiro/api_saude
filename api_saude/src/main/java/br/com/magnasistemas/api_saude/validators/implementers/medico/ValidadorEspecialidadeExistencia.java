package br.com.magnasistemas.api_saude.validators.implementers.medico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.repository.EspecialidadeRepository;

@Component
public class ValidadorEspecialidadeExistencia {

	@Autowired
	EspecialidadeRepository especialidadeRepository;
	
	protected boolean especialidadeExiste(List<Long> listaEspecialidades) {
		for (Long especialidade : listaEspecialidades) {
			if(!especialidadeRepository.existsById(especialidade)) {
				return false;
			}
		}
		return true;
	}
}
