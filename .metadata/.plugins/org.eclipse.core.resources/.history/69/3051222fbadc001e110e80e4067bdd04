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

import br.com.magnasistemas.api_saude.ApiSaudeApplication;
import br.com.magnasistemas.api_saude.dto.especialidade.DadosAtualizarEspecialidade;
import br.com.magnasistemas.api_saude.dto.especialidade.DadosCadastroEspecialidade;
import br.com.magnasistemas.api_saude.dto.especialidade.DadosDetalhamentoEspecialidade;
import br.com.magnasistemas.api_saude.dto.medico.DadosAtualizarMedico;
import br.com.magnasistemas.api_saude.dto.medico.DadosDetalhamentoMedico;
import br.com.magnasistemas.api_saude.entity.Especialidade;
import br.com.magnasistemas.api_saude.repository.EspecialidadeRepository;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ApiSaudeApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class EspecialidadeControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int randomServerPort;
	
	@Autowired
	EspecialidadeRepository especialidadeRepository;
	
	String urlBase = null;
	
	@BeforeEach
	void preparacaoTeste() {
		urlBase = "http://localhost:" + randomServerPort + "/especialidades";
		
		DadosCadastroEspecialidade dadosEsp = new DadosCadastroEspecialidade("ORTOPEDIA");
		
		Especialidade especialidade = new Especialidade(dadosEsp);
		
		especialidadeRepository.save(especialidade);
		
	}
	
	@AfterEach
	void limparRegistros() {
		especialidadeRepository.deleteAllAndReseteSequence();
	}
	
	@Test
	void criarEspecialidadeComSucesso() {
		DadosCadastroEspecialidade dadosEsp = new DadosCadastroEspecialidade("CARDIOLOGIA");
		
		ResponseEntity<DadosDetalhamentoEspecialidade> response = restTemplate.postForEntity(urlBase, dadosEsp, 
				DadosDetalhamentoEspecialidade.class);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	void listarEspecialidadesComSucesso() {
		ResponseEntity<DadosDetalhamentoEspecialidade> response = restTemplate.getForEntity(urlBase,
				DadosDetalhamentoEspecialidade.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void AtualizarEspecialidadeComSucesso() {
		
		DadosAtualizarEspecialidade dadosAtualizar = new DadosAtualizarEspecialidade(1L, "ENDOCRINOLOGIA");
	
		ResponseEntity<DadosDetalhamentoEspecialidade> response = restTemplate.exchange(
				urlBase, HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizar), DadosDetalhamentoEspecialidade.class, Void.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void AtualizarEspecialidadeComIdInexistente() {
		
		DadosAtualizarEspecialidade dadosAtualizar = new DadosAtualizarEspecialidade(3L, "ENDOCRINOLOGIA");
	
		ResponseEntity<DadosDetalhamentoEspecialidade> response = restTemplate.exchange(
				urlBase, HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizar), DadosDetalhamentoEspecialidade.class, Void.class);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	void deleteEspecialidadeComIdInexistente() {
		ResponseEntity<Void> response = restTemplate.exchange(urlBase + "/3",
				HttpMethod.DELETE, null, Void.class);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	void deleteEspecialidadeComSucesso() {
		ResponseEntity<Void> response = restTemplate.exchange(urlBase + "/1",
				HttpMethod.DELETE, null, Void.class);
		
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	
	@Test
	void detalharEspecialidadeComIdInexistente() {
		ResponseEntity<DadosDetalhamentoEspecialidade> response = restTemplate.getForEntity(urlBase + "/3",
				DadosDetalhamentoEspecialidade.class);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	void detalharEspecialidadeComSucesso() {
		ResponseEntity<DadosDetalhamentoEspecialidade> response = restTemplate.getForEntity(urlBase + "/1",
				DadosDetalhamentoEspecialidade.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
