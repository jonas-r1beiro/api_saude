package br.com.magnasistemas.api_saude.dto.consulta;

import java.sql.Timestamp;

import br.com.magnasistemas.api_saude.dto.medicoespecialidade.DadosDetalhamentoMedEsp;
import br.com.magnasistemas.api_saude.dto.paciente.DadosDetalhamentoPaciente;
import br.com.magnasistemas.api_saude.entity.Consulta;

public record DadosDetalhamentoConsulta(
		Long id,
		DadosDetalhamentoPaciente paciente,
		DadosDetalhamentoMedEsp medEsp,
		Timestamp dataHora
		) {
	
	public DadosDetalhamentoConsulta(Consulta consulta) {
		this(consulta.getId(),
				new DadosDetalhamentoPaciente(consulta.getFkPaciente()),
				new DadosDetalhamentoMedEsp(consulta.getFkMedEsp()),
				consulta.getDataHora());
	}

}
