package br.com.magnasistemas.api_saude.validators.implementers.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.dto.consulta.DadosCadastroConsulta;
import br.com.magnasistemas.api_saude.exception.ArgumentoInvalidoException;
import br.com.magnasistemas.api_saude.repository.EspecialidadeRepository;
import br.com.magnasistemas.api_saude.repository.MedicoRepository;
import br.com.magnasistemas.api_saude.repository.PacienteRepository;
import br.com.magnasistemas.api_saude.validators.interfaces.consulta.CadastrarConsultaValidator;

@Component
public class ValidadorConsultaCadastro implements CadastrarConsultaValidator {
	
	@Autowired
	MedicoRepository medicoRepository;
	
	@Autowired
	EspecialidadeRepository especialidadeRepository;
	
	@Autowired
	PacienteRepository pacienteRepository;
	
	@Autowired
	ValidadorHorario validadorHorario;

	@Override
	public void validador(DadosCadastroConsulta dados) {
		if(!medicoRepository.existsById(dados.idMedico())) {
			throw new ArgumentoInvalidoException("O médico passado não existe!");
		}
		
		if(!especialidadeRepository.existsById(dados.idEspecialidade())) {
			throw new ArgumentoInvalidoException("A especialidade passada não existe!");
		}
		
		if(!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ArgumentoInvalidoException("O paciente passado não existe!");
		}
		
		validadorHorario.validador(dados.dataHora(), dados.idPaciente(), dados.idMedico());

	}

}
