package br.com.magnasistemas.api_saude.dto.medico;

import br.com.magnasistemas.api_saude.dto.especialidade.DadosDetalhamentoEspecialidade;
import br.com.magnasistemas.api_saude.entity.Medico;
import java.util.List;

public record DadosDetalhamentoMedico(
		Long id,
		String nome,
		String crm,
		List<DadosDetalhamentoEspecialidade> especialidades
		) {

	public DadosDetalhamentoMedico(Medico medico, List<DadosDetalhamentoEspecialidade> dadosEsp) {
		this(medico.getId(), medico.getNome(), medico.getCrm(), dadosEsp);
	}
}
