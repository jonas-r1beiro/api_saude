package br.com.magnasistemas.api_saude.validators.implementers.medico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.dto.medico.DadosAtualizarMedico;
import br.com.magnasistemas.api_saude.entity.Medico;
import br.com.magnasistemas.api_saude.exception.ArgumentoInvalidoException;
import br.com.magnasistemas.api_saude.repository.MedicoRepository;
import br.com.magnasistemas.api_saude.validators.interfaces.medico.AtualizarMedicoValidator;

@Component
public class ValidadorMedicoAtualizacao implements AtualizarMedicoValidator {
	
	@Autowired
	ValidadorCrm validadorCrm;
	
	@Autowired
	ValidadorEspecialidadeExistencia validadorEspecialidade;
	
	@Autowired
	MedicoRepository medicoRepository;

	@Override
	public void validador(DadosAtualizarMedico dados) {
		
		Medico medico = medicoRepository.getReferenceById(dados.id());
		
		if(validadorCrm.crmExiste(dados.crm()) 
				&& !dados.crm().equals(medico.getCrm())) {
			throw new ArgumentoInvalidoException("Argumento inválido, o médico já existe!");
		}
		
		if(!validadorEspecialidade.especialidadeExiste(dados.especialidades())) {
			throw new ArgumentoInvalidoException("A especialidade passada não existe!");
		}

	}

}
