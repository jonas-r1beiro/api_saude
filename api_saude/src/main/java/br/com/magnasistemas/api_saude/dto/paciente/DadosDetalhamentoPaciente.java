package br.com.magnasistemas.api_saude.dto.paciente;

import java.sql.Date;

import br.com.magnasistemas.api_saude.entity.Paciente;

public record DadosDetalhamentoPaciente(
		Long id,
		String nome,
		Date dataNascimento,
		String cpf,
		String sexo,
		String alergias,
		String medicamentos
		) {
	
	public DadosDetalhamentoPaciente(Paciente paciente) {
		this(paciente.getId(), paciente.getNome(), paciente.getDataNascimento(), paciente.getCpf(),
				paciente.getSexo(), paciente.getAlergias(), paciente.getMedicamentos());
	}

}