package br.com.magnasistemas.api_saude.validators.interfaces.consulta;

import java.sql.Timestamp;

public interface HorarioPossivelConsultaValidator {
	
	void validador(Timestamp dataHora, Long idPaciente, Long idMedico);

}
