package br.com.magnasistemas.api_saude.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magnasistemas.api_saude.dto.especialidade.DadosAtualizarEspecialidade;
import br.com.magnasistemas.api_saude.dto.especialidade.DadosCadastroEspecialidade;
import br.com.magnasistemas.api_saude.dto.especialidade.DadosDetalhamentoEspecialidade;
import br.com.magnasistemas.api_saude.service.EspecialidadeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadeController {

	@Autowired
	EspecialidadeService especialidadeService;
	
	@Operation(description = "Cadastrar uma especialidade")
	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoEspecialidade> cadastrar(@RequestBody @Valid DadosCadastroEspecialidade dados){
		return ResponseEntity.status(201).body(especialidadeService.cadastro(dados));
	}
	
//	@Operation(description = "Lista todas as especialidedes")
//	@GetMapping
//	public ResponseEntity<Page<DadosDetalhamentoEspecialidade>> listar(
//			@PageableDefault(size = 100, page = 0, sort = "id") Pageable pageable){
//		return ResponseEntity.ok(especialidadeService.listar(pageable));
//	}
	
	@Operation(description = "Lista todas as especialidedes")
	@GetMapping
	public ResponseEntity<List<DadosDetalhamentoEspecialidade>> listar(){
		return ResponseEntity.ok(especialidadeService.listar());
	}
	
	@Operation(description = "Atualiza um registro de especialide")
	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoEspecialidade> atualizar (@RequestBody @Valid DadosAtualizarEspecialidade dados){
		return ResponseEntity.ok(especialidadeService.atualizar(dados));
	}
	
	@Operation(description = "Delete em um regisro de especialidade")
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<HttpStatus> excluir(@PathVariable Long id){
		especialidadeService.excluir(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(description = "Detalhar um registro de especialidade")
	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoEspecialidade> detalhar(@PathVariable Long id){
		return ResponseEntity.ok(especialidadeService.detalhar(id));
	}
}

