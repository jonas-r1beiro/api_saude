package br.com.magnasistemas.api_saude.dto.papel;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroPapel(
		@NotBlank(message = "O nome não pode estar vazio")
		String nome
		) {

}
