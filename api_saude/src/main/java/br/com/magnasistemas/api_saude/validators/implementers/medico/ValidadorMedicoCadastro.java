package br.com.magnasistemas.api_saude.validators.implementers.medico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.dto.medico.DadosCadastroMedico;
import br.com.magnasistemas.api_saude.exception.ArgumentoInvalidoException;
import br.com.magnasistemas.api_saude.validators.interfaces.medico.CadastrarMedicoValidator;

@Component
public class ValidadorMedicoCadastro implements CadastrarMedicoValidator {
	
	@Autowired
	ValidadorCrm validadorCrm;
	
	@Autowired
	ValidadorEspecialidadeExistencia validadorEspecialidade;

	@Override
	public void validador(DadosCadastroMedico dados) {
		if(validadorCrm.crmExiste(dados.crm())) {
			throw new ArgumentoInvalidoException("CRM inválido, pois ele já está atribuído a outro médido");
		}
		
		if(!validadorEspecialidade.especialidadeExiste(dados.especialidades())) {
			throw new ArgumentoInvalidoException("A especialidade passada não existe!");
		}

	}

}