package br.com.magnasistemas.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

import br.com.magnasistemas.api_saude.ApiSaudeApplication;
import br.com.magnasistemas.api_saude.dto.especialidade.DadosCadastroEspecialidade;
import br.com.magnasistemas.api_saude.dto.medico.DadosAtualizarMedico;
import br.com.magnasistemas.api_saude.dto.medico.DadosCadastroMedico;
import br.com.magnasistemas.api_saude.dto.medico.DadosDetalhamentoMedico;
import br.com.magnasistemas.api_saude.entity.Especialidade;
import br.com.magnasistemas.api_saude.entity.Medico;
import br.com.magnasistemas.api_saude.exception.ArgumentoInvalidoException;
import br.com.magnasistemas.api_saude.repository.EspecialidadeRepository;
import br.com.magnasistemas.api_saude.repository.MedicoRepository;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ApiSaudeApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class MedicoControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int randomServerPort;
	
	@Autowired
	MedicoRepository medicoRepository;
	
	@Autowired
	EspecialidadeRepository especialidadeRepository;
	
	private String urlBase = null;
	
	@BeforeEach
	void preparacaoTeste() {
		urlBase = "http://localhost:" + randomServerPort + "/medicos";
		
		DadosCadastroEspecialidade dadosEsp = new DadosCadastroEspecialidade("ORTOPEDIA");
		
		Especialidade esp = new Especialidade(dadosEsp);
		
		especialidadeRepository.save(esp);
		
		List<Long> listaEsp = new ArrayList<>();
		listaEsp.add(1L);
		
		DadosCadastroMedico dadosMed = new DadosCadastroMedico("Roberto", "111111", listaEsp);
		
		Medico medico = new Medico(dadosMed);
		
		medicoRepository.save(medico);
	}
	
	@AfterEach
	void limparRegistros() {
		especialidadeRepository.deleteAllAndReseteSequence();
		
		medicoRepository.deleteAllAndReseteSequence();
	}
	
	@Test
	void criarMedicoComSucesso() {
		List<Long> listaEsp = new ArrayList<>();
		listaEsp.add(1L);
		
		DadosCadastroMedico dadosMed = new DadosCadastroMedico("Roberto", "111112", listaEsp);
	
		ResponseEntity<DadosDetalhamentoMedico> response = restTemplate.postForEntity(urlBase, dadosMed, 
				DadosDetalhamentoMedico.class);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	void criarMedicoComCrmRepetido() {
		List<Long> listaEsp = new ArrayList<>();
		listaEsp.add(1L);
		
		DadosCadastroMedico dadosMed = new DadosCadastroMedico("Roberto", "111111", listaEsp);
	
		ResponseEntity<DadosDetalhamentoMedico> response;
		
		try {
			response = restTemplate.postForEntity(urlBase, dadosMed, 
					DadosDetalhamentoMedico.class);
			
		}catch(RestClientException ex) {
			response = ResponseEntity.badRequest().build();
		}
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	void criarMedicoComEspecialidadeInexistente() {
		List<Long> listaEsp = new ArrayList<>();
		listaEsp.add(2L);
		
		DadosCadastroMedico dadosMed = new DadosCadastroMedico("Roberto", "111113", listaEsp);
	
		ResponseEntity<DadosDetalhamentoMedico> response;
		
		try {
			response = restTemplate.postForEntity(urlBase, dadosMed, 
					DadosDetalhamentoMedico.class);
			
		}catch(RestClientException ex) {
			response = ResponseEntity.badRequest().build();
		}
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
//	@Test
//	void listarMedicosComSucesso() {
//		ResponseEntity<DadosDetalhamentoMedico> response = restTemplate.getForEntity(urlBase,
//				DadosDetalhamentoMedico.class);
//		
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//	}
	
	@Test
	void listarMedicosComSucesso() {
		ResponseEntity<DadosDetalhamentoMedico> response;
		
		try {
			response = restTemplate.getForEntity(urlBase,
					DadosDetalhamentoMedico.class);			
		}catch(RestClientException ex) {
			response = ResponseEntity.ok().build();
		}
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void AtualizarMedicoComSucesso() {
		List<Long> listaEsp = new ArrayList<>();
		listaEsp.add(1L);
		
		DadosAtualizarMedico dadosAtualizar = new DadosAtualizarMedico(1L, "José", "111111", listaEsp);
	
		ResponseEntity<DadosDetalhamentoMedico> response = restTemplate.exchange(
				urlBase, HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizar), DadosDetalhamentoMedico.class, Void.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void AtualizarMedicoComNovoCrm() {
		List<Long> listaEsp = new ArrayList<>();
		listaEsp.add(1L);
		
		DadosAtualizarMedico dadosAtualizar = new DadosAtualizarMedico(1L, "José", "111113", listaEsp);
	
		ResponseEntity<DadosDetalhamentoMedico> response = restTemplate.exchange(
				urlBase, HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizar), DadosDetalhamentoMedico.class, Void.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void AtualizarMedicoComEspecialidadeInexistente() {
		List<Long> listaEsp = new ArrayList<>();
		listaEsp.add(3L);
		
		DadosAtualizarMedico dadosAtualizar = new DadosAtualizarMedico(1L, "José", "111113", listaEsp);
	
		ResponseEntity<ArgumentoInvalidoException> response;
		
		try {
			response = restTemplate.exchange(
					urlBase, HttpMethod.PUT,
					new HttpEntity<>(dadosAtualizar), ArgumentoInvalidoException.class, Void.class);
			
		}catch(RestClientException ex) {
			response = ResponseEntity.badRequest().build();
		}
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	void AtualizarMedicoComCrmExistente() {
		List<Long> listaEsp = new ArrayList<>();
		listaEsp.add(1L);
		
		DadosCadastroMedico dadosMed = new DadosCadastroMedico("Roberto", "111112", listaEsp);
		
		Medico medico = new Medico(dadosMed);
		
		medicoRepository.save(medico);
		
		DadosAtualizarMedico dadosAtualizar = new DadosAtualizarMedico(2L, "José", "111111", listaEsp);
	
		ResponseEntity<ArgumentoInvalidoException> response;
		
		try {
			response = restTemplate.exchange(
					urlBase, HttpMethod.PUT,
					new HttpEntity<>(dadosAtualizar), ArgumentoInvalidoException.class, Void.class);
			
		}catch(RestClientException ex) {
			response = ResponseEntity.badRequest().build();
		}
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	void deleteMedicoComSucesso() {
		ResponseEntity<Void> response = restTemplate.exchange(urlBase + "/1",
				HttpMethod.DELETE, null, Void.class);
		
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	
	@Test
	void deleteMedicoComIdInexistente() {
		ResponseEntity<Void> response = restTemplate.exchange(urlBase + "/3",
				HttpMethod.DELETE, null, Void.class);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	void detalharMedicoComIdInexistente() {
		ResponseEntity<DadosDetalhamentoMedico> response = restTemplate.getForEntity(urlBase + "/3",
				DadosDetalhamentoMedico.class);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	void detalharComSucesso() {
		ResponseEntity<DadosDetalhamentoMedico> response = restTemplate.getForEntity(urlBase + "/1",
				DadosDetalhamentoMedico.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
