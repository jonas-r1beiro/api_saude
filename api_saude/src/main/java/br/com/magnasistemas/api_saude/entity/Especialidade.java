package br.com.magnasistemas.api_saude.entity;

import java.util.List;

import br.com.magnasistemas.api_saude.dto.especialidade.DadosCadastroEspecialidade;
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
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "tb_especialidade")
public class Especialidade {
	
	public Especialidade(DadosCadastroEspecialidade dados) {
		this.nome = dados.nome();
	}
	
	public Especialidade() {}
	
	@Positive(message = "Informe um número natural (maior que zero)")
    @Digits(integer = 10, fraction = 0, message = "Informe um número natural (sem parte decimal)")
	@NotNull(message = "O campo não pode ser nulo")
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
	
	@OneToMany(mappedBy = "fkEspecialidade", cascade = CascadeType.ALL)
	List<MedicoEspecialidade> medicoEspecialidade;
	
	
}
