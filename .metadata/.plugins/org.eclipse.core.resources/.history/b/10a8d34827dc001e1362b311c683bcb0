package br.com.magnasistemas.api_saude.dto.consulta;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.magnasistemas.api_saude.dto.especialidade.DadosDetalhamentoEspecialidade;
import br.com.magnasistemas.api_saude.dto.medico.DadosDetalhamentoMedico;
import br.com.magnasistemas.api_saude.dto.paciente.DadosDetalhamentoPaciente;
import br.com.magnasistemas.api_saude.entity.Consulta;
import br.com.magnasistemas.api_saude.entity.Especialidade;
import br.com.magnasistemas.api_saude.entity.Medico;
import br.com.magnasistemas.api_saude.repository.EspecialidadeRepository;

public record DadosDetalhamentoConsulta(
		Long id,
		DadosDetalhamentoPaciente paciente,
		DadosDetalhamentoMedico medico,
		DadosDetalhamentoEspecialidade especialidade,
		Timestamp dataHora
		) {
	
	public DadosDetalhamentoConsulta(Consulta consulta) {
		this(consulta.getId(),
				new DadosDetalhamentoPaciente(consulta.getFkPaciente()),
				new DadosDetalhamentoMedico(consulta.getFkMedico(), convertToEspDto(consulta)),
				new DadosDetalhamentoEspecialidade(consulta.getFkEspecialidade()),
				consulta.getDataHora());
	}
	
	
	static private List<DadosDetalhamentoEspecialidade> convertToEspDto(Consulta consulta) {
		List<DadosDetalhamentoEspecialidade> listEsp = new ArrayList();
		
		for (Especialidade especialidade : consulta.getFkMedico().getEspecialidades()) {
			listEsp.add(new DadosDetalhamentoEspecialidade(especialidade));
		}
		
		return listEsp;
	}

}
