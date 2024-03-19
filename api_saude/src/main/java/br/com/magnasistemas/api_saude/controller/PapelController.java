package br.com.magnasistemas.api_saude.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magnasistemas.api_saude.dto.papel.DadosDetalhamentoPapel;
import br.com.magnasistemas.api_saude.service.PapelService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/papeis")
public class PapelController {
	
	@Autowired
	PapelService papelService;

	@Operation(description = "Lista todas os papeis")
	@GetMapping
	public ResponseEntity<List<DadosDetalhamentoPapel>> listar(){
		return ResponseEntity.ok(papelService.listar());
	}
}
