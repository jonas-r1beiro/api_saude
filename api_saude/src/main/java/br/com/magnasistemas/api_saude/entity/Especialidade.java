package br.com.magnasistemas.api_saude.entity;

import java.util.List;

import br.com.magnasistemas.api_saude.dto.especialidade.DadosCadastroEspecialidade;
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

@Entity
@Table(name = "tb_especialidade")
public class Especialidade {
	
	public Especialidade(DadosCadastroEspecialidade dados) {
		this.nome = dados.nome();
	}
	
	public Especialidade() {}
	
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O nome não pode estar vazio")
	@Column(name = "nome")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}
	
	@ManyToMany
	@JoinTable(
		name = "tb_medico_especialidade",
		joinColumns = @JoinColumn(name = "fk_especialidade"),
		inverseJoinColumns = @JoinColumn(name = "fk_medico")
	)
	List<Medico> medicos;
	
	
}
