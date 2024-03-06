package br.com.magnasistemas.api_saude.entity;

import java.util.List;

import br.com.magnasistemas.api_saude.dto.medico.DadosCadastroMedico;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "tb_medico")
public class Medico {
	public Medico(DadosCadastroMedico dados) {
		this.nome = dados.nome();
		this.crm = dados.crm();
	}
	
	public Medico() {}
	
	@Positive(message = "Informe um número natural (maior que zero)")
    @Digits(integer = 10, fraction = 0, message = "Informe um número natural (sem parte decimal)")
	@NotNull(message = "O campo não pode ser nulo")
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
	
	@OneToMany(mappedBy = "fkMedico", cascade = CascadeType.ALL)
	private List<MedicoEspecialidade> medicoEspecialidade;
	
}
