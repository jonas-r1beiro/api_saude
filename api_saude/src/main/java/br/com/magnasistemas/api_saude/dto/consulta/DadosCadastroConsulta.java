package br.com.magnasistemas.api_saude.dto.consulta;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DadosCadastroConsulta(
		@Positive(message = "Informe um número natural (maior que zero)")
	    @Digits(integer = 10, fraction = 0, message = "Informe um número natural (sem parte decimal)")
		@NotNull(message = "O campo não pode ser nulo")
		Long idPaciente,
		@Positive(message = "Informe um número natural (maior que zero)")
	    @Digits(integer = 10, fraction = 0, message = "Informe um número natural (sem parte decimal)")
		@NotNull(message = "O campo não pode ser nulo")
		Long idMedEsp,
		@NotNull
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Brazil/Brasilia")
		Timestamp dataHora
		) {

}
