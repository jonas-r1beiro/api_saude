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
import jakarta.validation.Valid;

@Service
public class PacienteService {
	
	static final String MENSAGEMID = "O ID passado não existe!";
	
	@Autowired
	PacienteRepository pacienteRepository;
	
	public DadosDetalhamentoPaciente cadastro (@Valid DadosCadastroPaciente dados){
		Paciente paciente =  new Paciente(dados);	
		
		pacienteRepository.save(paciente);
		
		return new DadosDetalhamentoPaciente(paciente);
	}
	
	public Page<DadosDetalhamentoPaciente> listar(Pageable pageable){
		Page<Paciente> pagePacientes = pacienteRepository.findAll(pageable);
		
		Page<DadosDetalhamentoPaciente> pageDados = pagePacientes.map(DadosDetalhamentoPaciente::new);
		
		return pageDados;
		
	}
	
	public DadosDetalhamentoPaciente atualizar (@Valid DadosAtualizarPaciente dados){
		if(!pacienteRepository.existsById(dados.id())) {
//			return ResponseEntity.notFound().build();
		}
		
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
		if(!pacienteRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
		}
		
		pacienteRepository.deleteById(id);
		
	}
	
	public DadosDetalhamentoPaciente detalhar(Long id){
		if(!pacienteRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
		}
		
		Paciente paciente = pacienteRepository.getReferenceById(id);
		
		return new DadosDetalhamentoPaciente(paciente);
	}

}
