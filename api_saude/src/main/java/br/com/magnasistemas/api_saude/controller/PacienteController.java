package br.com.magnasistemas.api_saude.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magnasistemas.api_saude.dto.paciente.DadosAtualizarPaciente;
import br.com.magnasistemas.api_saude.dto.paciente.DadosCadastroPaciente;
import br.com.magnasistemas.api_saude.dto.paciente.DadosDetalhamentoPaciente;
import br.com.magnasistemas.api_saude.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
	
	@Autowired
	PacienteService pacienteService;

	@Operation(description = "Cadastrar um paciente")
	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(@RequestBody @Valid DadosCadastroPaciente dados){
		return pacienteService.cadastro(dados);
	}
	
	@Operation(description = "Lista todas os pacientes")
	@GetMapping
	public ResponseEntity<Page<DadosDetalhamentoPaciente>> listar(
			@PageableDefault(size = 100, page = 0, sort = "id") Pageable pageable){
		return pacienteService.listar(pageable);
	}
	
	@Operation(description = "Atualiza um registro de paciente")
	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoPaciente> atualizar (@RequestBody @Valid DadosAtualizarPaciente dados){
		return pacienteService.atualizar(dados);
	}
	
	@Operation(description = "Delete em um regisro de paciente")
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<HttpStatus> excluir(@PathVariable Long id){
		return pacienteService.excluir(id);
	}
	
	@Operation(description = "Detalhar um registro de paciente")
	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoPaciente> detalhar(@PathVariable Long id){
		return pacienteService.detalhar(id);
	}
}