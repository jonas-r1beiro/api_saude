package br.com.magnasistemas.api_saude.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements UserDetails {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O login não pode estar vazio")
	@Column(name = "login")
	private String login;
	
	@NotBlank(message = "A senha não pode estar vazia")
	@Column(name = "senha")
	private String senha;
	
	@Column(name = "id_externo")
	private Long idExterno;
	
	@NotNull(message = "O campo não pode ser nulo")
	@ManyToOne
	@JoinColumn(name = "fk_papel")
	private Papel papel;

	public Papel getPapel() {
		return papel;
	}

	public void setPapel(Papel papel) {
		this.papel = papel;
	}
	
	

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getIdExterno() {
		return this.idExterno;
	}

	public void setIdPaciente(Long idExterno) {
		this.idExterno = idExterno;
	}

	public Long getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(papel.getId() == 2) {
			return List.of(new SimpleGrantedAuthority("ROLE_PACIENTE"));
		}
		
		return List.of(new SimpleGrantedAuthority("ROLE_FUNCIONARIO"));
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
