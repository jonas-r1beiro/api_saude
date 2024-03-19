package br.com.magnasistemas.api_saude.dto.usuario;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DadosAtualizarUsuario(
		@Positive(message = "Informe um número natural (maior que zero)")
	    @Digits(integer = 10, fraction = 0, message = "Informe um número natural (sem parte decimal)")
		@NotNull(message = "O campo não pode ser nulo")
		Long id,
		@NotBlank(message = "O login não pode estar vazio")
		String login,
		@NotBlank(message = "A senha não pode estar vazia")
		String senha,
		Long idExterno,
		@Positive(message = "Informe um número natural (maior que zero)")
	    @Digits(integer = 10, fraction = 0, message = "Informe um número natural (sem parte decimal)")
		@NotNull(message = "O campo não pode ser nulo")
		Long idPapel
		) {

}
