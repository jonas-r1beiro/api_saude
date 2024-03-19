package br.com.magnasistemas.api_saude.dto.usuario;

import br.com.magnasistemas.api_saude.dto.papel.DadosDetalhamentoPapel;
import br.com.magnasistemas.api_saude.entity.Usuario;

public record DadosDetalhamentoUsuario(
		Long id,
		String login,
		Long idExterno,
		DadosDetalhamentoPapel papel
		) {
	
	public DadosDetalhamentoUsuario(Usuario usuario){
		this(usuario.getId(), usuario.getLogin(), usuario.getIdExterno(), 
				new DadosDetalhamentoPapel(usuario.getPapel()));
	}

}
