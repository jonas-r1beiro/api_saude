package br.com.magnasistemas.api_saude.validators.implementers.consulta;

import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.entity.Especialidade;
import br.com.magnasistemas.api_saude.entity.Medico;
import br.com.magnasistemas.api_saude.exception.ArgumentoInvalidoException;
import br.com.magnasistemas.api_saude.validators.interfaces.consulta.ConsultaEspecialidadeValidator;

@Component
public class ValidadorConsultaEspecialidade implements ConsultaEspecialidadeValidator{

	@Override
	public void validador(Especialidade especialidade, Medico medico) {
		for (Especialidade especialidadeIterable : medico.getEspecialidades()) {
			if(especialidadeIterable.getId() == especialidade.getId()) {
				return;
			}
		}
		
		throw new ArgumentoInvalidoException("O médico não possui a especialidade da consulta!");
		
	}

}
