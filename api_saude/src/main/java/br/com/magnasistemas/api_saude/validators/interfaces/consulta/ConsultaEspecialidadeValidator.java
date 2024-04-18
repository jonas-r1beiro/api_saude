package br.com.magnasistemas.api_saude.validators.interfaces.consulta;

import br.com.magnasistemas.api_saude.entity.Especialidade;
import br.com.magnasistemas.api_saude.entity.Medico;

public interface ConsultaEspecialidadeValidator {
	
	void validador(Especialidade especialidade, Medico medico);

}
