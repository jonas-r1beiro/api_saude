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
	
	@Column(name = "nome")
	private String nome;
	
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
