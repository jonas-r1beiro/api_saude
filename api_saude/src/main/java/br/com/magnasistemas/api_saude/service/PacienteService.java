package br.com.magnasistemas.api_saude.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.api_saude.dto.paciente.DadosAtualizarPaciente;
import br.com.magnasistemas.api_saude.dto.paciente.DadosCadastroPaciente;
import br.com.magnasistemas.api_saude.dto.paciente.DadosDetalhamentoPaciente;
import br.com.magnasistemas.api_saude.entity.Paciente;
import br.com.magnasistemas.api_saude.repository.PacienteRepository;
import br.com.magnasistemas.api_saude.validators.implementers.paciente.ValidadorPacienteAtualizacao;
import br.com.magnasistemas.api_saude.validators.implementers.paciente.ValidadorPacienteCadastro;
import br.com.magnasistemas.api_saude.validators.implementers.paciente.ValidadorPacienteDelete;
import br.com.magnasistemas.api_saude.validators.implementers.paciente.ValidadorPacienteDetalhar;
import jakarta.validation.Valid;

@Service
public class PacienteService {
	
	static final String MENSAGEMID = "O ID passado não existe!";
	
	@Autowired
	PacienteRepository pacienteRepository;
	
	@Autowired
	ValidadorPacienteAtualizacao validadorAtualizacao;
	
	@Autowired
	ValidadorPacienteDelete validadorDelete;
	
	@Autowired
	ValidadorPacienteDetalhar validadorDetalhar;
	
	@Autowired
	ValidadorPacienteCadastro validadorCadastro;
	
	public DadosDetalhamentoPaciente cadastro (@Valid DadosCadastroPaciente dados){
		validadorCadastro.validador(dados);
		
		Paciente paciente =  new Paciente(dados);	
		
		pacienteRepository.save(paciente);
		
		return new DadosDetalhamentoPaciente(paciente);
	}
	
	public Page<DadosDetalhamentoPaciente> listar(Pageable pageable){
		Page<Paciente> pagePacientes = pacienteRepository.findAll(pageable);
		
		return pagePacientes.map(DadosDetalhamentoPaciente::new);
		
	}
	
	public DadosDetalhamentoPaciente atualizar (@Valid DadosAtualizarPaciente dados){
		validadorAtualizacao.validador(dados);
		
		Paciente paciente = pacienteRepository.getReferenceById(dados.id());
		
		paciente.setNome(dados.nome());
		paciente.setDataNascimento(dados.dataNascimento());
		paciente.setCpf(dados.cpf());
		paciente.setSexo(dados.sexo());
		paciente.setAlergias(dados.alergias());
		paciente.setMedicamentos(dados.medicamentos());
		
		return new DadosDetalhamentoPaciente(paciente);
	}
	
	public void excluir(Long id){
		validadorDelete.validador(id);
		
		pacienteRepository.deleteById(id);
		
	}
	
	public DadosDetalhamentoPaciente detalhar(Long id){
		validadorDetalhar.validador(id);
		
		Paciente paciente = pacienteRepository.getReferenceById(id);
		
		return new DadosDetalhamentoPaciente(paciente);
	}

}
