package br.com.magnasistemas.api_saude.entity;

import java.sql.Date;


import br.com.magnasistemas.api_saude.dto.paciente.DadosCadastroPaciente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name = "tb_paciente")
public class Paciente {

	public Paciente(DadosCadastroPaciente dados) {
		this.nome = dados.nome();
		this.dataNascimento = dados.dataNascimento();
		this.cpf = dados.cpf();
		this.sexo = dados.sexo().toUpperCase();
		this.alergias = dados.alergias();
		this.medicamentos = dados.medicamentos();
	}
	
	public Paciente() {}
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O nome não pode estar vazio")
	@Column(name = "nome")
	private String nome;
	
	@NotNull
	@Column(name = "data_nascimento")
	private Date dataNascimento;
	
	@Pattern(regexp = "^\\d{11}$", message = "O CPF deve conter 11 números")
	@Column(name = "cpf")
	private String cpf;
	
	@Pattern(regexp = "^[fFmM]$", message = "O sexo deve ser representado por F ou M, sendo, respectivamente, Feminino e Masculino")
	@Column(name = "sexo")
	private String sexo;
	
	@Column(name = "alergias")
	private String alergias;
	
	@Column(name = "medicamentos")
	private String medicamentos;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo.toUpperCase();
	}

	public String getAlergias() {
		return alergias;
	}

	public void setAlergias(String alergias) {
		this.alergias = alergias;
	}

	public String getMedicamentos() {
		return medicamentos;
	}

	public void setMedicamentos(String medicamentos) {
		this.medicamentos = medicamentos;
	}

	public Long getId() {
		return id;
	}
	
	
}
