package br.com.magnasistemas.api_saude.validators.implementers.medico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.repository.MedicoRepository;

@Component
public class ValidadorCrm {
	@Autowired
	MedicoRepository medicoRepository;
	
	protected boolean crmExiste(String crm) {
		return  medicoRepository.existsByCrm(crm);
	}
}
