package br.com.magnasistemas.api_saude.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_medico_especialidade")
public class MedicoEspecialidade {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;

	
	@NotNull(message = "O campo não pode ser nulo")
	@ManyToOne
	@JoinColumn(name = "fk_medico")
	Medico fkMedico;
	
	@NotNull(message = "O campo não pode ser nulo")
	@ManyToOne
	@JoinColumn(name = "fk_especialidade")
	Especialidade fkEspecialidade;

}
