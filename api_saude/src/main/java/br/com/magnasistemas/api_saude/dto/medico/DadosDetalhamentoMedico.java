package br.com.magnasistemas.api_saude.dto.medico;

import br.com.magnasistemas.api_saude.entity.Medico;

public record DadosDetalhamentoMedico(
		Long id,
		String nome,
		String crm
		) {

	public DadosDetalhamentoMedico(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getCrm());
	}
}
