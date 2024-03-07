package br.com.magnasistemas.api_saude.validators.implementers.paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.dto.paciente.DadosAtualizarPaciente;
import br.com.magnasistemas.api_saude.entity.Paciente;
import br.com.magnasistemas.api_saude.exception.ArgumentoInvalidoException;
import br.com.magnasistemas.api_saude.repository.PacienteRepository;
import br.com.magnasistemas.api_saude.validators.interfaces.paciente.AtualizarPacienteValidator;
import jakarta.persistence.EntityNotFoundException;

@Component
public class ValidadorPacienteAtualizacao implements AtualizarPacienteValidator{

	@Autowired
	PacienteRepository pacienteRepository;
	
	@Override
	public void validador(DadosAtualizarPaciente dados) {
		if(!pacienteRepository.existsById(dados.id())) {
			throw new EntityNotFoundException();
		}
		
		Paciente paciente = pacienteRepository.getReferenceById(dados.id());
		
		if(pacienteRepository.existsByCpf(dados.cpf()) 
				&& !dados.cpf().equals(paciente.getCpf())) {
			throw new ArgumentoInvalidoException("O CPF passado já pertence a outro paciente!");
		}
	}

}
