package br.com.magnasistemas.api_saude.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	public ResponseEntity<DadosDetalhamentoPaciente> cadastro (@Valid DadosCadastroPaciente dados){
		Paciente paciente =  new Paciente(dados);	
		
		pacienteRepository.save(paciente);
		
		var uri = org.springframework.web.util.UriComponentsBuilder.fromUriString("pacientes/id")
				.buildAndExpand(paciente.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
	}
	
	public ResponseEntity<Page<DadosDetalhamentoPaciente>> listar(Pageable pageable){
		Page<Paciente> pagePacientes = pacienteRepository.findAll(pageable);
		
		Page<DadosDetalhamentoPaciente> pageDados = pagePacientes.map(DadosDetalhamentoPaciente::new);
		
		return ResponseEntity.ok(pageDados);
	}
	
	public ResponseEntity<DadosDetalhamentoPaciente> atualizar (@Valid DadosAtualizarPaciente dados){
		if(!pacienteRepository.existsById(dados.id())) {
			return ResponseEntity.notFound().build();
		}
		
		Paciente paciente = pacienteRepository.getReferenceById(dados.id());
		
		paciente.setNome(dados.nome());
		paciente.setDataNascimento(dados.dataNascimento());
		paciente.setCpf(dados.cpf());
		paciente.setSexo(dados.sexo());
		paciente.setAlergias(dados.alergias());
		paciente.setMedicamentos(dados.medicamentos());
		
		return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
	}
	
	public ResponseEntity<HttpStatus> excluir(Long id){
		if(!pacienteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		pacienteRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	public ResponseEntity<DadosDetalhamentoPaciente> detalhar(Long id){
		if(!pacienteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		Paciente paciente = pacienteRepository.getReferenceById(id);
		
		return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
	}

}
