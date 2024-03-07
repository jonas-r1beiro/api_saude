package br.com.magnasistemas.api_saude.validators.implementers.consulta;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.entity.Consulta;
import br.com.magnasistemas.api_saude.exception.ArgumentoInvalidoException;
import br.com.magnasistemas.api_saude.repository.ConsultaRepository;
import br.com.magnasistemas.api_saude.validators.interfaces.consulta.HorarioPossivelConsultaValidator;

@Component
public class ValidadorHorario implements HorarioPossivelConsultaValidator {
	
	@Autowired
	ConsultaRepository consultaRepository;

	@Override
	public void validador(Timestamp dataHora, Long idPaciente, Long idMedico) {
		LocalDateTime localDateTime = dataHora.toLocalDateTime();
		int horaConsulta = localDateTime.getHour();
		int diaSemana = localDateTime.getDayOfWeek().getValue();
		
		List<Consulta> consultaBanco1 = consultaRepository.consultaPorDia(idPaciente, dataHora);
		List<Consulta> consultaBanco2 = consultaRepository.horarioMedico(idMedico, dataHora);
		
		if(horaConsulta + 1 >= 18 
				|| horaConsulta < 9 
				|| diaSemana == 6 
				|| diaSemana == 7 
				|| !consultaBanco1.isEmpty()
				||  !consultaBanco2.isEmpty()) {
			throw new ArgumentoInvalidoException("Não é possível marcar uma consulta para essa data e horário!");
		}
	}

}
