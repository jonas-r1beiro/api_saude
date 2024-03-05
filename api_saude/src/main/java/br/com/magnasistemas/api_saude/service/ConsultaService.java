package br.com.magnasistemas.api_saude.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.api_saude.dto.consulta.DadosAtualizarConsulta;
import br.com.magnasistemas.api_saude.dto.consulta.DadosCadastroConsulta;
import br.com.magnasistemas.api_saude.dto.consulta.DadosDetalhamentoConsulta;
import br.com.magnasistemas.api_saude.entity.Consulta;
import br.com.magnasistemas.api_saude.repository.ConsultaRepository;
import br.com.magnasistemas.api_saude.repository.MedEspRepository;
import br.com.magnasistemas.api_saude.repository.PacienteRepository;
import jakarta.validation.Valid;

@Service
public class ConsultaService {

	static final String MENSAGEMID = "O ID passado não existe!";
	
	@Autowired
	ConsultaRepository consultaRepository;
	
	@Autowired
	MedEspRepository medEspRepository;
	
	@Autowired
	PacienteRepository pacienteRepository;
	
	public ResponseEntity<DadosDetalhamentoConsulta> cadastro (@Valid DadosCadastroConsulta dados){
		if(!medEspRepository.existsById(dados.idMedEsp())) {
			return ResponseEntity.notFound().build();
		}
		
		if(!pacienteRepository.existsById(dados.idPaciente())) {
			return ResponseEntity.notFound().build();
		}
		
		Long idMedico = medEspRepository.getReferenceById(dados.idMedEsp()).getFkMedico().getId();
		
		if(!consultaValida(dados.dataHora(), dados.idPaciente(), idMedico)) {
			return ResponseEntity.status(409).build();
		}
		
		
		Consulta consulta = new Consulta(
										pacienteRepository.getReferenceById(dados.idPaciente()),
										medEspRepository.getReferenceById(dados.idMedEsp()),
										dados.dataHora()
										);
		
		consultaRepository.save(consulta);
		
		var uri = org.springframework.web.util.UriComponentsBuilder.fromUriString("consultas/id")
				.buildAndExpand(consulta.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DadosDetalhamentoConsulta(consulta));
	}
	
	public ResponseEntity<Page<DadosDetalhamentoConsulta>> listar(Pageable pageable){
		Page<Consulta> pageConsulta = consultaRepository.findAll(pageable);
		
		Page<DadosDetalhamentoConsulta> pageDados = pageConsulta.map(DadosDetalhamentoConsulta::new);
		
		return ResponseEntity.ok(pageDados);
	}
	
	public ResponseEntity<DadosDetalhamentoConsulta> atualizar (@Valid DadosAtualizarConsulta dados){
		if(!consultaRepository.existsById(dados.id())) {
			return ResponseEntity.notFound().build();
		}
		
		if(!medEspRepository.existsById(dados.idMedEsp())) {
			return ResponseEntity.notFound().build();
		}
		
		if(!pacienteRepository.existsById(dados.idPaciente())) {
			return ResponseEntity.notFound().build();
		}
		
		Long idMedico = medEspRepository.getReferenceById(dados.idMedEsp()).getFkMedico().getId();
		
		if(!consultaValida(dados.dataHora(), dados.idPaciente(), idMedico)) {
			return ResponseEntity.status(409).build();
		}
		
		Consulta consulta = consultaRepository.getReferenceById(dados.id());
		
		consulta.setFkPaciente(pacienteRepository.getReferenceById(dados.idPaciente()));
		consulta.setFkMedEsp(medEspRepository.getReferenceById(dados.idMedEsp()));
		consulta.setDataHora(dados.dataHora());
		
		return ResponseEntity.ok(new DadosDetalhamentoConsulta(consulta));
	}
	
	public ResponseEntity<HttpStatus> excluir(Long id){
		if(!consultaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		consultaRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	public ResponseEntity<DadosDetalhamentoConsulta> detalhar(Long id){
		if(!consultaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		Consulta consulta = consultaRepository.getReferenceById(id);
		
		return ResponseEntity.ok(new DadosDetalhamentoConsulta(consulta));
	}
	
	private boolean consultaValida(Timestamp dataHora, Long idPaciente, Long idMedico) {
		LocalDateTime localDateTime = dataHora.toLocalDateTime();
		int horaConsulta = localDateTime.getHour();
		int diaSemana = localDateTime.getDayOfWeek().getValue();
		
		List<Consulta> consultaBanco1 = consultaRepository.consultaPorDia(idPaciente, dataHora);
		List<Consulta> consultaBanco2 = consultaRepository.horarioMedico(idMedico, dataHora);
		
		/*
		if(horaConsulta + 1 >= 18 || horaConsulta < 9 || diaSemana == 6 || diaSemana == 7 || !consultaBanco1.isEmpty()
				||  !listaConsultas.isEmpty()) {
			return false;
		}
		*/
		
		if(horaConsulta + 1 >= 18 || horaConsulta < 9 || diaSemana == 6 || diaSemana == 7 
				|| !consultaBanco1.isEmpty() || !consultaBanco2.isEmpty()) {
			return false;
		}
		
		
		
		return true;
	}
}
