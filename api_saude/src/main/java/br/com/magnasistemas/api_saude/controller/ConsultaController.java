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
		return consultaService.cadastro(dados);
	}
	
	@Operation(description = "Lista todas as consultas")
	@GetMapping
	public ResponseEntity<Page<DadosDetalhamentoConsulta>> listar(
			@PageableDefault(size = 100, page = 0, sort = "id") Pageable pageable){
		return consultaService.listar(pageable);
	}
	
	@Operation(description = "Atualiza um registro de consulta")
	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoConsulta> atualizar (@RequestBody @Valid DadosAtualizarConsulta dados){
		return consultaService.atualizar(dados);
	}
	
	@Operation(description = "Delete em um regisro de consulta")
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<HttpStatus> excluir(@PathVariable Long id){
		return consultaService.excluir(id);
	}
	
	@Operation(description = "Detalhar um registro de consulta")
	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoConsulta> detalhar(@PathVariable Long id){
		return consultaService.detalhar(id);
	}
}
