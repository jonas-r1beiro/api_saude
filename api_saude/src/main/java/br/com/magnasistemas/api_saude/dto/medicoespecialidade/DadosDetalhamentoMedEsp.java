package br.com.magnasistemas.api_saude.dto.medicoespecialidade;

import br.com.magnasistemas.api_saude.dto.especialidade.DadosDetalhamentoEspecialidade;
import br.com.magnasistemas.api_saude.dto.medico.DadosDetalhamentoMedico;
import br.com.magnasistemas.api_saude.entity.MedicoEspecialidade;

public record DadosDetalhamentoMedEsp(
		Long id,
		DadosDetalhamentoMedico medico,
		DadosDetalhamentoEspecialidade especialidade
		) {
	
	public DadosDetalhamentoMedEsp(MedicoEspecialidade medEsp) {
		this(medEsp.getId(), 
				new DadosDetalhamentoMedico(medEsp.getFkMedico()),
				new DadosDetalhamentoEspecialidade(medEsp.getFkEspecialidade()));
	}

}
