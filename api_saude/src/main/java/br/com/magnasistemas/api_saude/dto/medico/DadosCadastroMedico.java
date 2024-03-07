package br.com.magnasistemas.api_saude.dto.medico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;

public record DadosCadastroMedico(
		@NotBlank(message = "O nome não pode estar vazio")
		String nome,
		@Pattern(regexp = "^\\d{6}$", message = "O CRM deve conter 6 números")
		String crm,
		@NotNull(message = "A lista de especialidades não pode ser nula")
		List<Long> especialidades
		) {
}
