package br.com.magnasistemas.api_saude.validators.implementers.especialidade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.dto.especialidade.DadosCadastroEspecialidade;
import br.com.magnasistemas.api_saude.exception.ArgumentoInvalidoException;
import br.com.magnasistemas.api_saude.repository.EspecialidadeRepository;
import br.com.magnasistemas.api_saude.validators.interfaces.especialidade.CadastrarEspecialidadeValidator;

@Component
public class ValidadorEspecialidadeCadastro implements CadastrarEspecialidadeValidator {

	@Autowired
	EspecialidadeRepository especialidadeRepository;
	
	@Override
	public void validador(DadosCadastroEspecialidade dados) {
		if(especialidadeRepository.existsByNome(dados.nome())) {
			throw new ArgumentoInvalidoException("Já existe uma especialidade com esse mesmo nome!");
		}

	}

}
