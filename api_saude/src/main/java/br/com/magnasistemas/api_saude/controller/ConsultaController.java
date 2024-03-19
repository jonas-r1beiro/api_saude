package br.com.magnasistemas.api_saude.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.magnasistemas.api_saude.dto.consulta.DadosAtualizarConsulta;
import br.com.magnasistemas.api_saude.dto.consulta.DadosCadastroConsulta;
import br.com.magnasistemas.api_saude.dto.consulta.DadosDetalhamentoConsulta;
import br.com.magnasistemas.api_saude.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
	
	@Autowired
	ConsultaService consultaService;

	@Operation(description = "Cadastrar uma consulta")
	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoConsulta> cadastrar(@RequestBody @Valid DadosCadastroConsulta dados){
		return ResponseEntity.status(201).body(consultaService.cadastro(dados));
	}
	
	@Operation(description = "Lista todas as consultas")
	@GetMapping
	public ResponseEntity<List<DadosDetalhamentoConsulta>> listar(){
		return ResponseEntity.ok(consultaService.listar());
	}
	
	@Operation(description = "Atualiza um registro de consulta")
	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoConsulta> atualizar (@RequestBody @Valid DadosAtualizarConsulta dados){
		return ResponseEntity.ok(consultaService.atualizar(dados));
	}
	
	@Operation(description = "Delete em um regisro de consulta")
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<HttpStatus> excluir(@PathVariable Long id){
		consultaService.excluir(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@Operation(description = "Detalhar um registro de consulta")
	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoConsulta> detalhar(@PathVariable Long id){
		return ResponseEntity.ok(consultaService.detalhar(id));
	}
	
	@Operation(description = "Retorna uma lista de consultas baseada no CPF passado")
	@GetMapping("/pesquisa_cpf/{cpf}")
	public ResponseEntity<List<DadosDetalhamentoConsulta>> listarPorCpf(@PathVariable String cpf){
		return ResponseEntity.ok(consultaService.listarPorCpf(cpf));
	}
	
	@Operation(description = "Retorna uma lista de consultas baseada no ID do paciente passado")
	@GetMapping("/pesquisa_id_paciente/{id}")
	public ResponseEntity<List<DadosDetalhamentoConsulta>> listarPorIdPaciente(@PathVariable Long id){
		return ResponseEntity.ok(consultaService.listarPorIdCliente(id));
	}
}
