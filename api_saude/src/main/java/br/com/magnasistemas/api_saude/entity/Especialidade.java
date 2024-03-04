package br.com.magnasistemas.api_saude.entity;

import br.com.magnasistemas.api_saude.dto.especialidade.DadosCadastroEspecialidade;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_especialidade")
public class Especialidade {
	
	public Especialidade(DadosCadastroEspecialidade dados) {
		this.nome = dados.nome();
	}
	
	public Especialidade() {
		
	}
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
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
	
	
}
