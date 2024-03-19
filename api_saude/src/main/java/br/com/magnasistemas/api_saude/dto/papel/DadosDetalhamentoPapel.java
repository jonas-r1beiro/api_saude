package br.com.magnasistemas.api_saude.dto.papel;

import br.com.magnasistemas.api_saude.entity.Papel;

public record DadosDetalhamentoPapel(
		Long id,
		String nome
		) {
	
	public DadosDetalhamentoPapel(Papel papel) {
		this(papel.getId(), papel.getNome());
	}

}
