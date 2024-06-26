package br.com.magnasistemas.api_saude.validators.implementers.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.dto.consulta.DadosAtualizarConsulta;
import br.com.magnasistemas.api_saude.exception.ArgumentoInvalidoException;
import br.com.magnasistemas.api_saude.repository.ConsultaRepository;
import br.com.magnasistemas.api_saude.repository.EspecialidadeRepository;
import br.com.magnasistemas.api_saude.repository.MedicoRepository;
import br.com.magnasistemas.api_saude.repository.PacienteRepository;
import br.com.magnasistemas.api_saude.validators.interfaces.consulta.AtualizarConsultaValidator;
import jakarta.persistence.EntityNotFoundException;

@Component
public class ValidadorConsultaAtualizacao implements AtualizarConsultaValidator {
	
	@Autowired
	ConsultaRepository consultaRepository;
	
	@Autowired
	MedicoRepository medicoRepository;
	
	@Autowired
	EspecialidadeRepository especialidadeRepository;
	
	@Autowired
	PacienteRepository pacienteRepository;
	
	@Autowired
	ValidadorHorario validadorHorario;
	
	@Autowired
	ValidadorConsultaEspecialidade validadorEspecialidade;

	@Override
	public void validador(DadosAtualizarConsulta dados) {
		if(!consultaRepository.existsById(dados.id())) {
			throw new EntityNotFoundException();
		}
		
		if(!medicoRepository.existsById(dados.idMedico())) {
			throw new ArgumentoInvalidoException("O médico passado não existe!");
		}
		
		if(!especialidadeRepository.existsById(dados.idEspecialidade())) {
			throw new ArgumentoInvalidoException("A especialidade passada não existe!");
		}
		
		if(!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ArgumentoInvalidoException("O paciente passado não existe!");
		}
		
		validadorEspecialidade.validador(especialidadeRepository.getReferenceById(dados.idEspecialidade()), medicoRepository.getReferenceById(dados.idMedico()));
		
		validadorHorario.validador(dados.dataHora(), dados.idPaciente(), dados.idMedico());

	}

}
