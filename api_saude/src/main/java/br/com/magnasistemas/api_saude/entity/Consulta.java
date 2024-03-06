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
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "tb_consulta")
public class Consulta {

	public Consulta(Paciente paciente, MedicoEspecialidade medEsp, Timestamp dataHora) {
		this.fkPaciente = paciente;
		this.fkMedEsp = medEsp;
		this.dataHora = dataHora;
	}
	
	public Consulta() {}
	
	@Positive(message = "Informe um número natural (maior que zero)")
    @Digits(integer = 10, fraction = 0, message = "Informe um número natural (sem parte decimal)")
	@NotNull(message = "O campo não pode ser nulo")
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "O campo não pode ser nulo")
	@ManyToOne
	@JoinColumn(name = "fk_paciente")
	private Paciente fkPaciente;
	
	@NotNull(message = "O campo não pode ser nulo")
	@ManyToOne
	@JoinColumn(name = "fk_medico_especialidade")
	private MedicoEspecialidade fkMedEsp;
	
	@NotNull
	@Column(name = "data_hora")
	private Timestamp dataHora;

	public Paciente getFkPaciente() {
		return fkPaciente;
	}

	public void setFkPaciente(Paciente fkPaciente) {
		this.fkPaciente = fkPaciente;
	}

	public MedicoEspecialidade getFkMedEsp() {
		return fkMedEsp;
	}

	public void setFkMedEsp(MedicoEspecialidade fkMedEsp) {
		this.fkMedEsp = fkMedEsp;
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
