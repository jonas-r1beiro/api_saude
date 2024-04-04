package br.com.magnasistemas.api_saude.security;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.magnasistemas.api_saude.entity.Paciente;
import br.com.magnasistemas.api_saude.entity.Usuario;
import br.com.magnasistemas.api_saude.exception.ArgumentoInvalidoException;
import br.com.magnasistemas.api_saude.repository.PacienteRepository;
import br.com.magnasistemas.api_saude.repository.UsuarioRepository;
import br.com.magnasistemas.api_saude.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		String tokenJWT = recuperarToken(request);
		
		if(tokenJWT != null) {
			String subject = tokenService.getSubject(tokenJWT);
			Usuario usuario = usuarioRepository.findByLogin(subject);
			
			
			
			var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		
			if(usuario.getPapel().getId() == 2) {
				
				Pattern pattern = Pattern.compile(".*/([^/]+)/[^/]+");
				
				Matcher matcher = pattern.matcher(request.getRequestURI());
				
				
				if(matcher.matches()) {
					
					int indexValorFinal = request.getRequestURI().lastIndexOf('/');
					
					String valorFinalString = request.getRequestURI().substring(indexValorFinal + 1);
					
					if(matcher.group(1).equals("pesquisa_id_paciente")) {
						
						if(usuario.getPapel().getId() == 2 && !valorFinalString.equals(usuario.getIdExterno().toString())) {
							SecurityContextHolder.getContext().setAuthentication(null);
							throw new ArgumentoInvalidoException("O ID passado não corresponde ao ID do Paciente");
						}			
						
					}else if(matcher.group(1).equals("pesquisa_cpf")) {
						
						Paciente paciente = pacienteRepository.findById(usuario.getIdExterno()).get();
						
						if(!paciente.getCpf().equalsIgnoreCase(valorFinalString)) {
							SecurityContextHolder.getContext().setAuthentication(null);
							System.out.println("Chegou aqui!");
							throw new ArgumentoInvalidoException("O CPF passado não corresponde ao CPF do paciente");
						}
						
						
					}else if(matcher.group(1).equalsIgnoreCase("pacientes") && (!valorFinalString.equals(usuario.getIdExterno().toString()))) {
							SecurityContextHolder.getContext().setAuthentication(null);
							throw new ArgumentoInvalidoException("O ID passado para a pesquisa não corresponse ao ID do Paciente");
					}
				}
				
				
			}
			
		}
		
		
		filterChain.doFilter(request, response);
	}
	
	
	private String recuperarToken(HttpServletRequest request) {
		var authorizationHeader = request.getHeader("Authorization");
		
		if(authorizationHeader != null) {
			return authorizationHeader.replace("Bearer ", "");
		}
		
		return null;
	}

}
