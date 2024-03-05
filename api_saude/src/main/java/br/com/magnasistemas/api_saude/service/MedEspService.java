package br.com.magnasistemas.api_saude.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.api_saude.dto.medicoespecialidade.DadosAtualizarMedEsp;
import br.com.magnasistemas.api_saude.dto.medicoespecialidade.DadosCadastroMedEsp;
import br.com.magnasistemas.api_saude.dto.medicoespecialidade.DadosDetalhamentoMedEsp;
import br.com.magnasistemas.api_saude.entity.MedicoEspecialidade;
import br.com.magnasistemas.api_saude.repository.EspecialidadeRepository;
import br.com.magnasistemas.api_saude.repository.MedEspRepository;
import br.com.magnasistemas.api_saude.repository.MedicoRepository;
import jakarta.validation.Valid;

@Service
public class MedEspService {
	
	static final String MENSAGEMID = "O ID passado não existe!";
	
	@Autowired
	MedEspRepository medEspRepository;
	
	@Autowired
	MedicoRepository medicoRepository;
	
	@Autowired
	EspecialidadeRepository especialidadeRepository;
	
	public ResponseEntity<DadosDetalhamentoMedEsp> cadastro (@Valid DadosCadastroMedEsp dados){
		if(!medicoRepository.existsById(dados.idMedico())) {
			return ResponseEntity.notFound().build();
		}
		
		if(!especialidadeRepository.existsById(dados.idEspecialidade())) {
			return ResponseEntity.notFound().build();
		}
		
		MedicoEspecialidade medEsp = new MedicoEspecialidade(
										medicoRepository.getReferenceById(dados.idMedico()),
										especialidadeRepository.getReferenceById(dados.idEspecialidade()));
		
		medEspRepository.save(medEsp);
		
		var uri = org.springframework.web.util.UriComponentsBuilder.fromUriString("medicoEspecialidade/id")
				.buildAndExpand(medEsp.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DadosDetalhamentoMedEsp(medEsp));
	}
	
	public ResponseEntity<Page<DadosDetalhamentoMedEsp>> listar(Pageable pageable){
		Page<MedicoEspecialidade> pageMedEsp = medEspRepository.findAll(pageable);
		
		Page<DadosDetalhamentoMedEsp> pageDados = pageMedEsp.map(DadosDetalhamentoMedEsp::new);
		
		return ResponseEntity.ok(pageDados);
	}
	
	public ResponseEntity<DadosDetalhamentoMedEsp> atualizar (@Valid DadosAtualizarMedEsp dados){
		if(!especialidadeRepository.existsById(dados.id())) {
			return ResponseEntity.notFound().build();
		}
		
		if(!medicoRepository.existsById(dados.idMedico())) {
			return ResponseEntity.notFound().build();
		}
		
		if(!especialidadeRepository.existsById(dados.idEspecialidade())) {
			return ResponseEntity.notFound().build();
		}
		
		MedicoEspecialidade medEsp = medEspRepository.getReferenceById(dados.id());
		
		medEsp.setFkMedico(medicoRepository.getReferenceById(dados.idMedico()));
		medEsp.setFkEspecialidade(especialidadeRepository.getReferenceById(dados.idEspecialidade()));
		
		return ResponseEntity.ok(new DadosDetalhamentoMedEsp(medEsp));
	}
	
	public ResponseEntity<HttpStatus> excluir(Long id){
		if(!medEspRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		medEspRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	public ResponseEntity<DadosDetalhamentoMedEsp> detalhar(Long id){
		if(!medEspRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		MedicoEspecialidade medEsp = medEspRepository.getReferenceById(id);
		
		return ResponseEntity.ok(new DadosDetalhamentoMedEsp(medEsp));
	}
}