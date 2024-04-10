package br.com.magnasistemas.api_saude.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	
	@Autowired
	private SecurityFilter securityFilter;
	
	private static final String FUNCIONARIO = "FUNCIONARIO";
	private static final String PACIENTE = "PACIENTE";
	
	@Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(req ->{
			req.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
			req.requestMatchers(HttpMethod.POST, "/login").permitAll();
			req.requestMatchers(HttpMethod.GET, "/consultas/pesquisa_id_paciente/**").hasAnyRole(PACIENTE, FUNCIONARIO);
			req.requestMatchers(HttpMethod.GET, "/consultas/pesquisa_cpf/**").hasAnyRole(PACIENTE, FUNCIONARIO);
			req.requestMatchers(HttpMethod.GET, "/pacientes/**").hasAnyRole(PACIENTE, FUNCIONARIO);
			req.anyRequest().hasRole(FUNCIONARIO);
		})
        .sessionManagement(sm -> 
			sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		)
        .csrf(csrf -> csrf.disable())
        .oauth2ResourceServer(req ->
        	req.jwt(jwt ->
        		jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
        	)
        )
		.cors(cors -> cors.disable())
		.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();

    }
	
	
	
	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		
		converter.setJwtGrantedAuthoritiesConverter(
				jwt ->{
					List<String> userRoleAuthorities = jwt.getClaimAsStringList("authorities");
					
					if(userRoleAuthorities == null){
						return Collections.emptyList();						
					}
					
					JwtGrantedAuthoritiesConverter scopesConverter = new JwtGrantedAuthoritiesConverter();
					
					Collection<GrantedAuthority> scopeAuthorities = scopesConverter.convert(jwt);
					
					scopeAuthorities.addAll(userRoleAuthorities.stream()
							.map(SimpleGrantedAuthority::new)
							.toList());
					
					return scopeAuthorities;
				}
				);
		return converter;
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
		return configuration.getAuthenticationManager();
	}
	

	
}
