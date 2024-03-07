package br.com.magnasistemas.api_saude.dto.medico;

import java.util.List;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record DadosAtualizarMedico(
		@Positive(message = "Informe um número natural (maior que zero)")
	    @Digits(integer = 10, fraction = 0, message = "Informe um número natural (sem parte decimal)")
		@NotNull(message = "O campo não pode ser nulo")
		Long id,
		@NotBlank(message = "O nome não pode estar vazio")
		String nome,
		@Pattern(regexp = "^\\d{6}$", message = "O CRM deve conter 6 números")
		String crm,
		@NotNull
		List<Long> Especialidades
		) {

}
