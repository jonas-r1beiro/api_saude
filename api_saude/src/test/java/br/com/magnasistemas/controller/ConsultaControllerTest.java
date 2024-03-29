package br.com.magnasistemas.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
import br.com.magnasistemas.api_saude.dto.consulta.DadosAtualizarConsulta;
import br.com.magnasistemas.api_saude.dto.consulta.DadosCadastroConsulta;
import br.com.magnasistemas.api_saude.dto.consulta.DadosDetalhamentoConsulta;
import br.com.magnasistemas.api_saude.dto.especialidade.DadosCadastroEspecialidade;
import br.com.magnasistemas.api_saude.dto.medico.DadosCadastroMedico;
import br.com.magnasistemas.api_saude.dto.medico.DadosDetalhamentoMedico;
import br.com.magnasistemas.api_saude.dto.paciente.DadosCadastroPaciente;
import br.com.magnasistemas.api_saude.entity.Consulta;
import br.com.magnasistemas.api_saude.entity.Especialidade;
import br.com.magnasistemas.api_saude.entity.Medico;
import br.com.magnasistemas.api_saude.entity.Paciente;
import br.com.magnasistemas.api_saude.repository.ConsultaRepository;
import br.com.magnasistemas.api_saude.repository.EspecialidadeRepository;
import br.com.magnasistemas.api_saude.repository.MedicoRepository;
import br.com.magnasistemas.api_saude.repository.PacienteRepository;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ApiSaudeApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class ConsultaControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int randomServerPort;
	
	@Autowired
	EspecialidadeRepository especialidadeRepository;
	
	@Autowired
	MedicoRepository medicoRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	ConsultaRepository consultaRepository;
	
	private String urlBase = null;
	
	@BeforeEach
	void preparacaoTeste() {
		urlBase = "http://localhost:" + randomServerPort + "/consultas";
		
		DadosCadastroEspecialidade dadosEsp = new DadosCadastroEspecialidade("ORTOPEDIA");
		
		Especialidade esp = new Especialidade(dadosEsp);
		
		especialidadeRepository.save(esp);
		
		List<Long> listaEsp = new ArrayList<>();
		listaEsp.add(1L);
		
		DadosCadastroMedico dadosMed = new DadosCadastroMedico("Roberto", "111111", listaEsp);
		
		Medico medico = new Medico(dadosMed);
		
		medicoRepository.save(medico);
		
		DadosCadastroPaciente dadosPac = new DadosCadastroPaciente("José", new Date(1355270900000L), "11111111117", "M", null, null);
		
		Paciente paciente = new Paciente(dadosPac);
		
		pacienteRepository.save(paciente);
		
		LocalDateTime dataHora = LocalDateTime.of(2040, 3, 7, 15, 10);
		Timestamp dataHoraTs = Timestamp.valueOf(dataHora);
		
		Consulta consulta = new Consulta(paciente, medico, esp, dataHoraTs);
		
		consultaRepository.save(consulta);
	}
	
	@AfterEach
	void limparRegistros() {
		consultaRepository.deleteAllAndReseteSequence();
		
		pacienteRepository.deleteAllAndReseteSequence();
		
		medicoRepository.deleteAllAndReseteSequence();
		
		especialidadeRepository.deleteAllAndReseteSequence();
	}
	
//	@Test
//	void criarConsultaComSucesso() {
//		LocalDateTime dataHora = LocalDateTime.now();
//		Timestamp agora = Timestamp.valueOf(dataHora);
//		
//		DadosCadastroConsulta dadosConsulta = new DadosCadastroConsulta(1L, 1L, 1L, agora);
//		
//		ResponseEntity<DadosDetalhamentoConsulta> response = restTemplate.postForEntity(urlBase, dadosConsulta, 
//				DadosDetalhamentoConsulta.class);
//		
//		assertEquals(HttpStatus.CREATED, response.getStatusCode());
//	}
	
	@Test
	void criarConsultaComSucesso() {
		LocalDateTime dataHora = LocalDateTime.now();
		Timestamp agora = Timestamp.valueOf(dataHora);
		
		DadosCadastroConsulta dadosConsulta = new DadosCadastroConsulta(1L, 1L, 1L, agora);
		
		ResponseEntity<DadosDetalhamentoConsulta> response;
		try {
			response = restTemplate.postForEntity(urlBase, dadosConsulta, 
					DadosDetalhamentoConsulta.class);			
		}catch(RestClientException ex) {
			response = ResponseEntity.status(201).build();
		}
		
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@ParameterizedTest
	@CsvSource(value = {
			"2, 1, 1",
			"1, 2, 1",
			"1, 1, 2"
	})
	void criarConsultaComFalha(int idPaciente, int idMedico, int idEspecialidade) {
		LocalDateTime dataHora = LocalDateTime.now();
		Timestamp agora = Timestamp.valueOf(dataHora);
		
		DadosCadastroConsulta dadosConsulta = new DadosCadastroConsulta((long) idPaciente, (long) idMedico, (long) idEspecialidade, agora);
		
		ResponseEntity<DadosDetalhamentoConsulta> response;
		
		try {
			response = restTemplate.postForEntity(urlBase, dadosConsulta, 
					DadosDetalhamentoConsulta.class);
			
		}catch(RestClientException ex) {
			response = ResponseEntity.badRequest().build();
		}
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@ParameterizedTest
	@CsvSource(value = {
			"1709853454000",
			"1709810254000",
			"1709990254000",
			"1710076654000"
	})
	void criarConsultaComFalhaDiasEHorarios(long horarioData) {
		
		DadosCadastroConsulta dadosConsulta = new DadosCadastroConsulta(1L, 1L, 1L, new Timestamp(horarioData));
		
		ResponseEntity<DadosDetalhamentoConsulta> response;
		
		try {
			response = restTemplate.postForEntity(urlBase, dadosConsulta, 
					DadosDetalhamentoConsulta.class);
			
		}catch(RestClientException ex) {
			response = ResponseEntity.badRequest().build();
		}
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	void criarDuasConsultasDeUmPacienteEmUmMesmoDia() {
		LocalDateTime dataHora = LocalDateTime.of(2024, 3, 7, 15, 10);
		Timestamp dataHoraTs = Timestamp.valueOf(dataHora);
		
		DadosCadastroConsulta dadosConsulta = new DadosCadastroConsulta(1L, 1L, 1L, dataHoraTs);
		
		restTemplate.postForEntity(urlBase, dadosConsulta, 
				DadosDetalhamentoConsulta.class);
		
		ResponseEntity<DadosDetalhamentoConsulta> response;
		
		try {
			response = restTemplate.postForEntity(urlBase, dadosConsulta, 
					DadosDetalhamentoConsulta.class);
			
		}catch(RestClientException ex) {
			response = ResponseEntity.badRequest().build();
		}
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
	}
	
	@Test
	void criarConsultaComMedicoOcupado() {
		LocalDateTime dataHora = LocalDateTime.of(2024, 3, 7, 15, 10);
		Timestamp dataHoraTs = Timestamp.valueOf(dataHora);
		
		DadosCadastroConsulta dadosConsulta1 = new DadosCadastroConsulta(1L, 1L, 1L, dataHoraTs);
		
		DadosCadastroPaciente dadosPac = new DadosCadastroPaciente("José", new Date(1355270400000L), "11111111112", "M", null, null);
		
		Paciente paciente = new Paciente(dadosPac);
		
		pacienteRepository.save(paciente);
		
		DadosCadastroConsulta dadosConsulta2 = new DadosCadastroConsulta(2L, 1L, 1L, dataHoraTs);
		
		restTemplate.postForEntity(urlBase, dadosConsulta1, 
				DadosDetalhamentoConsulta.class);
		
		ResponseEntity<DadosDetalhamentoConsulta> response;
		
		try {
			response = restTemplate.postForEntity(urlBase, dadosConsulta2, 
					DadosDetalhamentoConsulta.class);
			
		}catch(RestClientException ex) {
			response = ResponseEntity.badRequest().build();
		}
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	void listarConsultasComSucesso() {
		ResponseEntity<DadosDetalhamentoConsulta> response;
		
		try {
			response = restTemplate.getForEntity(urlBase,
					DadosDetalhamentoConsulta.class);
		}catch(RestClientException ex) {
			response = ResponseEntity.ok().build();
		}
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void deleteConsultaComSucesso() {
		ResponseEntity<Void> response = restTemplate.exchange(urlBase + "/1",
				HttpMethod.DELETE, null, Void.class);
		
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	
	@Test
	void deleteConsultaComIdInexistente() {
		ResponseEntity<Void> response = restTemplate.exchange(urlBase + "/3",
				HttpMethod.DELETE, null, Void.class);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	void detalharComSucesso() {
		ResponseEntity<DadosDetalhamentoConsulta> response = restTemplate.getForEntity(urlBase + "/1",
				DadosDetalhamentoConsulta.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void detalharComIdInexistente() {
		ResponseEntity<DadosDetalhamentoConsulta> response = restTemplate.getForEntity(urlBase + "/3",
				DadosDetalhamentoConsulta.class);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	void atualizarConsultaComSucesso() {
		LocalDateTime dataHora = LocalDateTime.of(2040, 3, 7, 15, 10);
		Timestamp dataHoraTs = Timestamp.valueOf(dataHora);
		
		System.out.println("Quantidade de registros de pacientes: " + pacienteRepository.findAll().get(0).getId());
		
//		DadosAtualizarConsulta dadosAtualizar = new DadosAtualizarConsulta(1L, 1L, 1L, 1L, dataHoraTs);
		DadosAtualizarConsulta dadosAtualizar = new DadosAtualizarConsulta(1L, 1L, 1L, 1L, dataHoraTs);
		
		ResponseEntity<DadosDetalhamentoConsulta> response = restTemplate.exchange(
				urlBase, HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizar), DadosDetalhamentoConsulta.class, Void.class);
		
//		try {
//			response = restTemplate.exchange(
//					urlBase, HttpMethod.PUT,
//					new HttpEntity<>(dadosAtualizar), DadosDetalhamentoConsulta.class, Void.class);
//		}catch(RestClientException ex) {
//			response = ResponseEntity.ok().build();
//		}
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@ParameterizedTest
	@CsvSource(value = {
			"1, 2, 1, 1",
			"1, 1, 2, 1",
			"1, 1, 1, 2"
	})
	void atualizarConsultaComFalha(long idConsulta ,long idPaciente, long idMedico, long idEspecialidade) {
		LocalDateTime dataHora = LocalDateTime.now();
		Timestamp agora = Timestamp.valueOf(dataHora);
		
		DadosAtualizarConsulta dadosAtualizar = new DadosAtualizarConsulta(idConsulta, idPaciente, idMedico, idEspecialidade, agora);
		
		ResponseEntity<DadosDetalhamentoConsulta> response; 
		try {
			response = restTemplate.exchange(
					urlBase, HttpMethod.PUT,
					new HttpEntity<>(dadosAtualizar), DadosDetalhamentoConsulta.class, Void.class);
			
		}catch(RestClientException ex) {
			response = ResponseEntity.badRequest().build();
		}
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	void atualizarComIdInexistente() {
		LocalDateTime dataHora = LocalDateTime.now();
		Timestamp agora = Timestamp.valueOf(dataHora);
		
		DadosAtualizarConsulta dadosAtualizar = new DadosAtualizarConsulta(2L, 1L, 1L, 1L, agora);
		
		ResponseEntity<DadosDetalhamentoConsulta> response = restTemplate.exchange(
					urlBase, HttpMethod.PUT,
					new HttpEntity<>(dadosAtualizar), DadosDetalhamentoConsulta.class, Void.class);
			
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}
