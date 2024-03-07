package br.com.magnasistemas.api_saude.validators.implementers.paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.dto.paciente.DadosCadastroPaciente;
import br.com.magnasistemas.api_saude.exception.ArgumentoInvalidoException;
import br.com.magnasistemas.api_saude.repository.PacienteRepository;
import br.com.magnasistemas.api_saude.validators.interfaces.paciente.CadastrarPacienteValidator;

@Component
public class ValidadorPacienteCadastro implements CadastrarPacienteValidator {
	
	@Autowired
	PacienteRepository pacienteRepository;

	@Override
	public void validador(DadosCadastroPaciente dados) {
		if(pacienteRepository.existsByCpf(dados.cpf())) {
			throw new ArgumentoInvalidoException("O CPF passado já existe!");
		}
		
	}

}
