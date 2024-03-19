package br.com.magnasistemas.api_saude.validators.implementers.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.dto.usuario.DadosCadastroUsuario;
import br.com.magnasistemas.api_saude.entity.Papel;
import br.com.magnasistemas.api_saude.exception.ArgumentoInvalidoException;
import br.com.magnasistemas.api_saude.repository.PacienteRepository;
import br.com.magnasistemas.api_saude.repository.PapelRepository;
import br.com.magnasistemas.api_saude.validators.interfaces.usuario.CadastrarUsuarioValidator;

@Component
public class ValidadorUsuarioCadastro implements CadastrarUsuarioValidator {
	
	@Autowired
	PapelRepository papelRepository;
	
	@Autowired
	PacienteRepository pacienteRepository;

	@Override
	public void validador(DadosCadastroUsuario dados) {
		if(!papelRepository.existsById(dados.idPapel())) {
			throw new ArgumentoInvalidoException("O ID passado para o papel não existe!");
		}
		
		Papel papel = papelRepository.getReferenceById(dados.idPapel());
		
		if(papel.getId() == 2 && !pacienteRepository.existsById(dados.idExterno())) {
			throw new ArgumentoInvalidoException("Não existe nenhum paciente com o ID passado");
		}
		
	}

}
