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

import br.com.magnasistemas.api_saude.dto.medicoespecialidade.DadosAtualizarMedEsp;
import br.com.magnasistemas.api_saude.dto.medicoespecialidade.DadosCadastroMedEsp;
import br.com.magnasistemas.api_saude.dto.medicoespecialidade.DadosDetalhamentoMedEsp;
import br.com.magnasistemas.api_saude.service.MedEspService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicoEspecialidade")
public class MedEspController {

	@Autowired
	MedEspService medEspService;
	
	@Operation(description = "Cadastrar um medicoEspecialidade")
	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoMedEsp> cadastrar(@RequestBody @Valid DadosCadastroMedEsp dados){
		return medEspService.cadastro(dados);
	}
	
	@Operation(description = "Lista todas os medicoEspecialidade")
	@GetMapping
	public ResponseEntity<Page<DadosDetalhamentoMedEsp>> listar(
			@PageableDefault(size = 100, page = 0, sort = "id") Pageable pageable){
		return medEspService.listar(pageable);
	}
	
	@Operation(description = "Atualiza um registro de medicoEspecialidade")
	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoMedEsp> atualizar (@RequestBody @Valid DadosAtualizarMedEsp dados){
		return medEspService.atualizar(dados);
	}
	
	@Operation(description = "Delete em um regisro de medicoEspecialidade")
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<HttpStatus> excluir(@PathVariable Long id){
		return medEspService.excluir(id);
	}
	
	@Operation(description = "Detalhar um registro de medicoEspecialidade")
	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoMedEsp> detalhar(@PathVariable Long id){
		return medEspService.detalhar(id);
	}
}
