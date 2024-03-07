package br.com.magnasistemas.controller;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import br.com.magnasistemas.api_saude.ApiSaudeApplication;
import br.com.magnasistemas.api_saude.dto.paciente.DadosAtualizarPaciente;
import br.com.magnasistemas.api_saude.dto.paciente.DadosCadastroPaciente;
import br.com.magnasistemas.api_saude.dto.paciente.DadosDetalhamentoPaciente;
import br.com.magnasistemas.api_saude.exception.ArgumentoInvalidoException;
import br.com.magnasistemas.api_saude.repository.PacienteRepository;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ApiSaudeApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class PacienteControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int randomServerPort;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	private String urlBase = null;
	
	@BeforeEach
	void criarUrlBase() {
		urlBase = "http://localhost:" + randomServerPort + "/pacientes";
	}
	
	@AfterEach
	void apagarRegistro() {
		pacienteRepository.deleteAllAndReseteSequence();
	}
	
	@Test
	void criarPacienteComSucesso() {
		DadosCadastroPaciente dados = new DadosCadastroPaciente("José", new Date(1355270400000L), "11111111111", "M", null, null);
		
		ResponseEntity<DadosDetalhamentoPaciente> response = restTemplate.postForEntity(urlBase, dados, 
				DadosDetalhamentoPaciente.class);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	void criarPacienteComCpfRepetido() {
		DadosCadastroPaciente dados1 = new DadosCadastroPaciente("José", new Date(1355270400000L), "11111111111", "M", null, null);
		
		restTemplate.postForEntity(urlBase, dados1, 
				DadosDetalhamentoPaciente.class);
		
		DadosCadastroPaciente dados2 = new DadosCadastroPaciente("José", new Date(1355270400000L), "11111111111", "M", null, null);
		
		ResponseEntity<ArgumentoInvalidoException> response = null;
		
		try{
			response = restTemplate.postForEntity(urlBase, dados2, 
					ArgumentoInvalidoException.class);
		}catch(RestClientException ex) {
			response = ResponseEntity.badRequest().build();
		}
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	void listarPacientesComSucesso() {
		ResponseEntity<DadosDetalhamentoPaciente> response = restTemplate.getForEntity(urlBase,
				DadosDetalhamentoPaciente.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void atualizarComIdInexistente(){
		
		DadosAtualizarPaciente dadosAtualizar = new DadosAtualizarPaciente(1L, "josé", new Date(1355270400000L), "11111111111", "M", "", "");;
		ResponseEntity<DadosDetalhamentoPaciente> response = restTemplate.exchange(
				urlBase, HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizar), DadosDetalhamentoPaciente.class, Void.class);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	void atualizarComIdEexistente(){
		DadosCadastroPaciente dados1 = new DadosCadastroPaciente("José", new Date(1355270400000L), "11111111111", "M", null, null);
		
		restTemplate.postForEntity(urlBase, dados1, 
				DadosDetalhamentoPaciente.class);
		
		DadosAtualizarPaciente dadosAtualizar = new DadosAtualizarPaciente(1L, "josé", new Date(1355270400000L), "11111111111", "M", "", "");;
		ResponseEntity<DadosDetalhamentoPaciente> response = restTemplate.exchange(
				urlBase, HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizar), DadosDetalhamentoPaciente.class, Void.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void atualizarComCpfNovo(){
		DadosCadastroPaciente dados1 = new DadosCadastroPaciente("José", new Date(1355270400000L), "11111111111", "M", null, null);
		
		restTemplate.postForEntity(urlBase, dados1, 
				DadosDetalhamentoPaciente.class);
		
		DadosAtualizarPaciente dadosAtualizar = new DadosAtualizarPaciente(1L, "josé", new Date(1355270400000L), "11111111112", "M", "", "");;
		ResponseEntity<DadosDetalhamentoPaciente> response = restTemplate.exchange(
				urlBase, HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizar), DadosDetalhamentoPaciente.class, Void.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void atualizarComCpfDeOutroPaciente(){
		DadosCadastroPaciente dados1 = new DadosCadastroPaciente("José", new Date(1355270400000L), "11111111111", "M", null, null);
		
		restTemplate.postForEntity(urlBase, dados1, 
				DadosDetalhamentoPaciente.class);
		
		DadosCadastroPaciente dados2 = new DadosCadastroPaciente("José", new Date(1355270400000L), "11111111112", "M", null, null);
		
		restTemplate.postForEntity(urlBase, dados2, 
				DadosDetalhamentoPaciente.class);
		
		
		DadosAtualizarPaciente dadosAtualizar = new DadosAtualizarPaciente(2L, "josé", new Date(1355270400000L), "11111111111", "M", "", "");;
		ResponseEntity<ArgumentoInvalidoException> response = null;
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
	void deleteComIdInexistente() {
		ResponseEntity<Void> response = restTemplate.exchange(urlBase + "/1",
				HttpMethod.DELETE, null, Void.class);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	void deleteComSucesso() {
		DadosCadastroPaciente dados1 = new DadosCadastroPaciente("José", new Date(1355270400000L), "11111111111", "M", null, null);
		
		restTemplate.postForEntity(urlBase, dados1, 
				DadosDetalhamentoPaciente.class);
		
		ResponseEntity<Void> response = restTemplate.exchange(urlBase + "/1",
				HttpMethod.DELETE, null, Void.class);
		
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	
	@Test
	void detalharComIdInexistente() {
		ResponseEntity<DadosDetalhamentoPaciente> response = restTemplate.getForEntity(urlBase + "/1",
				DadosDetalhamentoPaciente.class);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	void detalharComSucesso() {
		DadosCadastroPaciente dados1 = new DadosCadastroPaciente("José", new Date(1355270400000L), "11111111111", "M", null, null);
		
		restTemplate.postForEntity(urlBase, dados1, 
				DadosDetalhamentoPaciente.class);
		
		ResponseEntity<DadosDetalhamentoPaciente> response = restTemplate.getForEntity(urlBase + "/1",
				DadosDetalhamentoPaciente.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
