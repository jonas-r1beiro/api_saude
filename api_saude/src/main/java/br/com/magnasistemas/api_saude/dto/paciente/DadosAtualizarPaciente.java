package br.com.magnasistemas.api_saude.dto.paciente;

import java.sql.Date;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record DadosAtualizarPaciente(
		@Positive(message = "Informe um número natural (maior que zero)")
	    @Digits(integer = 10, fraction = 0, message = "Informe um número natural (sem parte decimal)")
		@NotNull(message = "O campo não pode ser nulo")
		Long id,
		@NotBlank(message = "O nome não pode estar vazio")
		String nome,
		@NotNull
		Date dataNascimento,
		@Pattern(regexp = "^\\d{11}$", message = "O CPF deve conter 11 números")
		String cpf,
		@Pattern(regexp = "^[fFmM]$", message = "O sexo deve ser representado por F ou M, sendo, respectivamente, Feminino e Masculino")
		String sexo,
		String alergias,
		String medicamentos
		) {
	
	

}
