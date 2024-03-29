package br.com.magnasistemas.api_saude.entity;

import java.util.List;

import br.com.magnasistemas.api_saude.dto.medico.DadosCadastroMedico;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "tb_medico")
public class Medico {
	public Medico(DadosCadastroMedico dados) {
		this.nome = dados.nome();
		this.crm = dados.crm();
	}
	
	public Medico() {}
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O nome não pode estar vazio")
	@Column(name = "nome")
	private String nome;
	
	@Pattern(regexp = "^\\d{6}$", message = "O CRM deve conter 6 números")
	@Column(name = "crm")
	private String crm;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	}

	public Long getId() {
		return id;
	}
	
	@ManyToMany
	@JoinTable(
		name = "tb_medico_especialidade",
		joinColumns = @JoinColumn(name = "fk_medico"),
		inverseJoinColumns = @JoinColumn(name = "fk_especialidade")
	)
	List<Especialidade> especialidades;

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}
	
	
	
}
