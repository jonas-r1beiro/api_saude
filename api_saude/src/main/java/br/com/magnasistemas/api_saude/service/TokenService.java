package br.com.magnasistemas.api_saude.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.magnasistemas.api_saude.entity.Usuario;
import br.com.magnasistemas.api_saude.exception.ArgumentoInvalidoException;

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String secret;
	
	@Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
	private String issuerUri;
	
	private static final String ISSUER = "API_SAUDE";
	
	public String gerarToken(Usuario usuario) {
		try {
			var algoritmo = Algorithm.HMAC256(secret);
			
			return JWT.create()
					.withIssuer(ISSUER)
					.withSubject(usuario.getLogin())
					.withExpiresAt(dataExpiracao())
					.withClaim("idExterno", usuario.getIdExterno())
					.withClaim("idPapel", usuario.getPapel().getId())
					.sign(algoritmo);			
		}catch(JWTCreationException ex) {
			throw new ArgumentoInvalidoException("erro ao gerar token jwt");
		}
	}
	
	public String getSubject(String tokenJWT) {
		try {
			
			Jwt jwt = deserializarToken().decode(tokenJWT);
				
			return jwt.getSubject();		
		
		}catch(JWTVerificationException ex) {
			throw new ArgumentoInvalidoException("Token JWT inválido ou expirado!");
		}
	}
	
	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
		
	}
	
	private JwtDecoder deserializarToken() {
		JwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(issuerUri).build();

        return new JwtDecoder() {
            @Override
            public Jwt decode(String token) throws JwtException {
            	return jwtDecoder.decode(token);
            }
        };
	}
}
