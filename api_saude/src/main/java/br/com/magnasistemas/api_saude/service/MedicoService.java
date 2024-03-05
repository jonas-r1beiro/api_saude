package br.com.magnasistemas.api_saude.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.api_saude.dto.medico.DadosAtualizarMedico;
import br.com.magnasistemas.api_saude.dto.medico.DadosCadastroMedico;
import br.com.magnasistemas.api_saude.dto.medico.DadosDetalhamentoMedico;
import br.com.magnasistemas.api_saude.entity.Medico;
import br.com.magnasistemas.api_saude.repository.MedicoRepository;
import jakarta.validation.Valid;

@Service
public class MedicoService {

	static final String MENSAGEMID = "O ID passado não existe!";
	
	@Autowired
	MedicoRepository medicoRepository;
	
	public ResponseEntity<DadosDetalhamentoMedico> cadastro (@Valid DadosCadastroMedico dados){
		if(crmExiste(dados.crm())) {
			return ResponseEntity.status(409).build();
		}
		
		Medico medico =  new Medico(dados);	
		
		medicoRepository.save(medico);
		
		var uri = org.springframework.web.util.UriComponentsBuilder.fromUriString("medicos/id")
				.buildAndExpand(medico.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
	}
	
	public ResponseEntity<Page<DadosDetalhamentoMedico>> listar(Pageable pageable){
		Page<Medico> pageMedicos = medicoRepository.findAll(pageable);
		
		Page<DadosDetalhamentoMedico> pageDados = pageMedicos.map(DadosDetalhamentoMedico::new);
		
		return ResponseEntity.ok(pageDados);
	}
	
	public ResponseEntity<DadosDetalhamentoMedico> atualizar (@Valid DadosAtualizarMedico dados){
		if(!medicoRepository.existsById(dados.id())) {
			return ResponseEntity.notFound().build();
		}
		
		Medico medico = medicoRepository.getReferenceById(dados.id());
		
		medico.setNome(dados.nome());
		
		if(crmExiste(dados.crm()) && !dados.crm().equals(medico.getCrm())) {
			return ResponseEntity.status(409).build();
		}
		
		medico.setCrm(dados.crm());
		
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}
	
	public ResponseEntity<HttpStatus> excluir(Long id){
		if(!medicoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		medicoRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	public ResponseEntity<DadosDetalhamentoMedico> detalhar(Long id){
		if(!medicoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		Medico medico = medicoRepository.getReferenceById(id);
		
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}
	
	
	private boolean crmExiste(String crm) {
		return  medicoRepository.existsByCrm(crm);
	}
}