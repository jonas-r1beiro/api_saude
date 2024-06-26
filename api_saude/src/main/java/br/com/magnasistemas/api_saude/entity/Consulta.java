package br.com.magnasistemas.api_saude.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_consulta")
public class Consulta {

	public Consulta(Paciente paciente, Medico medico, Especialidade especialidade,Timestamp dataHora) {
		this.fkPaciente = paciente;
		this.fkMedico = medico;
		this.dataHora = dataHora;
		this.fkEspecialidade = especialidade;
	}
	
	public Consulta() {}
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "O campo não pode ser nulo")
	@ManyToOne
	@JoinColumn(name = "fk_paciente")
	private Paciente fkPaciente;
	
	@NotNull(message = "O campo não pode ser nulo")
	@ManyToOne
	@JoinColumn(name = "fk_medico")
	private Medico fkMedico;
	
	@NotNull(message = "O campo não pode ser nulo")
	@ManyToOne
	@JoinColumn(name = "fk_especialidade")
	private Especialidade fkEspecialidade;
	
	@NotNull
	@Column(name = "data_hora")
	private Timestamp dataHora;

	public Paciente getFkPaciente() {
		return fkPaciente;
	}

	public Medico getFkMedico() {
		return fkMedico;
	}

	public void setFkMedico(Medico fkMedico) {
		this.fkMedico = fkMedico;
	}

	public Especialidade getFkEspecialidade() {
		return fkEspecialidade;
	}

	public void setFkEspecialidade(Especialidade fkEspecialidade) {
		this.fkEspecialidade = fkEspecialidade;
	}

	public void setFkPaciente(Paciente fkPaciente) {
		this.fkPaciente = fkPaciente;
	}

	public Timestamp getDataHora() {
		return dataHora;
	}

	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}

	public Long getId() {
		return id;
	}
	
	
}
