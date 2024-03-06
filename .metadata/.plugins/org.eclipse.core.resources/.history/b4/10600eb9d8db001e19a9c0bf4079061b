package br.com.magnasistemas.api_saude.dto.medico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroMedico(
		@NotBlank(message = "O nome não pode estar vazio")
		String nome,
		@Pattern(regexp = "^\\d{6}$", message = "O CRM deve conter 6 números")
		String crm
		) {
}
