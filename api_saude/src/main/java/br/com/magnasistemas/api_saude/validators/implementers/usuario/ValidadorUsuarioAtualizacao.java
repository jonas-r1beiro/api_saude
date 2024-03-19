package br.com.magnasistemas.api_saude.validators.implementers.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.dto.usuario.DadosAtualizarUsuario;
import br.com.magnasistemas.api_saude.exception.ArgumentoInvalidoException;
import br.com.magnasistemas.api_saude.repository.PacienteRepository;
import br.com.magnasistemas.api_saude.repository.PapelRepository;
import br.com.magnasistemas.api_saude.repository.UsuarioRepository;
import br.com.magnasistemas.api_saude.validators.interfaces.usuario.AtualizarUsuarioValidator;
import jakarta.persistence.EntityNotFoundException;

@Component
public class ValidadorUsuarioAtualizacao implements AtualizarUsuarioValidator {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	PapelRepository papelRepository;
	
	@Autowired
	PacienteRepository pacienteRepository;

	@Override
	public void validador(DadosAtualizarUsuario dados) {
		if(!usuarioRepository.existsById(dados.id())) {
			throw new EntityNotFoundException();
		}
		
		if(!papelRepository.existsById(dados.idPapel())) {
			throw new ArgumentoInvalidoException("O papel passado não existe");
		}
		
		if(dados.idPapel() == 2 && !pacienteRepository.existsById(dados.idExterno())) {
			throw new ArgumentoInvalidoException("O paciente passado não existe!");
		}
		
	}

}
