package br.com.magnasistemas.api_saude.security;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.api_saude.entity.Paciente;
import br.com.magnasistemas.api_saude.entity.Usuario;
import br.com.magnasistemas.api_saude.repository.PacienteRepository;
import br.com.magnasistemas.api_saude.repository.UsuarioRepository;

@Component
public class UserSecurity implements AuthorizationManager<RequestAuthorizationContext> {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	PacienteRepository pacienteRepository;

	@Override
	public AuthorizationDecision check(Supplier supplier, RequestAuthorizationContext rac) {
		Long usuarioId = Long.parseLong(rac.getVariables().get("idExterno"));

		
		return new AuthorizationDecision(hasUserId(usuarioId));
	}
	
	private boolean hasUserId(Long usuarioId) {
		Usuario usuario = usuarioRepository.getReferenceById(usuarioId);
		
		if(usuario.getIdExterno() != null) {
			Paciente paciente = pacienteRepository.getReferenceById(usuario.getIdExterno());
			
			return paciente.getId() == usuario.getIdExterno();
		}
		
		return true;
	}

}
