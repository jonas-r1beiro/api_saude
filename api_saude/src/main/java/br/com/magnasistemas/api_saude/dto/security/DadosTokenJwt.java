package br.com.magnasistemas.api_saude.dto.security;

import jakarta.validation.constraints.NotBlank;

public record DadosTokenJwt(
		@NotBlank
		String token
		) {

}
