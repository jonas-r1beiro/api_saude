package br.com.magnasistemas.api_saude.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.api_saude.dto.especialidade.DadosAtualizarEspecialidade;
import br.com.magnasistemas.api_saude.dto.especialidade.DadosCadastroEspecialidade;
import br.com.magnasistemas.api_saude.dto.especialidade.DadosDetalhamentoEspecialidade;
import br.com.magnasistemas.api_saude.entity.Especialidade;
import br.com.magnasistemas.api_saude.repository.EspecialidadeRepository;
import jakarta.validation.Valid;

@Service
public class EspecialidadeService {
	
	static final String MENSAGEMID = "O ID passado não existe!";
	
	@Autowired
	EspecialidadeRepository especialidadeRepository;

	public ResponseEntity<DadosDetalhamentoEspecialidade> cadastro (@Valid DadosCadastroEspecialidade dados){
		Especialidade especialidade =  new Especialidade(dados);	
		
		especialidadeRepository.save(especialidade);
		
		var uri = org.springframework.web.util.UriComponentsBuilder.fromUriString("especialidades/id")
				.buildAndExpand(especialidade.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DadosDetalhamentoEspecialidade(especialidade));
	}
	
	public ResponseEntity<Page<DadosDetalhamentoEspecialidade>> listar(Pageable pageable){
		Page<Especialidade> pageEspecialidades = especialidadeRepository.findAll(pageable);
		
		Page<DadosDetalhamentoEspecialidade> pageDados = pageEspecialidades.map(DadosDetalhamentoEspecialidade::new);
		
		return ResponseEntity.ok(pageDados);
	}
	
	public ResponseEntity<DadosDetalhamentoEspecialidade> atualizar (@Valid DadosAtualizarEspecialidade dados){
		if(!especialidadeRepository.existsById(dados.id())) {
			return ResponseEntity.notFound().build();
		}
		
		Especialidade especialidade = especialidadeRepository.getReferenceById(dados.id());
		
		especialidade.setNome(dados.nome());
		
		return ResponseEntity.ok(new DadosDetalhamentoEspecialidade(especialidade));
	}
	
	public ResponseEntity<HttpStatus> excluir(Long id){
		if(!especialidadeRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		especialidadeRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	public ResponseEntity<DadosDetalhamentoEspecialidade> detalhar(Long id){
		if(!especialidadeRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		Especialidade especialidade = especialidadeRepository.getReferenceById(id);
		
		return ResponseEntity.ok(new DadosDetalhamentoEspecialidade(especialidade));
	}
}
