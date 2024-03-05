package br.com.magnasistemas.api_saude.dto.especialidade;

import br.com.magnasistemas.api_saude.entity.Especialidade;

public record DadosDetalhamentoEspecialidade(
		Long id,
		String nome
		) {
	public DadosDetalhamentoEspecialidade(Especialidade especialidade) {
		this(especialidade.getId(), especialidade.getNome());
	}
}
