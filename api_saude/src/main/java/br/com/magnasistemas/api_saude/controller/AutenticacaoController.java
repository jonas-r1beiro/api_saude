package br.com.magnasistemas.api_saude.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magnasistemas.api_saude.dto.security.DadosTokenJwt;
import br.com.magnasistemas.api_saude.dto.usuario.DadosAutenticacao;
import br.com.magnasistemas.api_saude.entity.Usuario;
import br.com.magnasistemas.api_saude.service.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping 
	public ResponseEntity<DadosTokenJwt> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
		var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		
		var authentication = manager.authenticate(authenticationToken);
		
		var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
		
		return ResponseEntity.ok(new DadosTokenJwt(tokenJWT));
	}
	
}
