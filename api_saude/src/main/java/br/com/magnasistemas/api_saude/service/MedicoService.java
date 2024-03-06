package br.com.magnasistemas.api_saude.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.api_saude.dto.especialidade.DadosDetalhamentoEspecialidade;
import br.com.magnasistemas.api_saude.dto.medico.DadosAtualizarMedico;
import br.com.magnasistemas.api_saude.dto.medico.DadosCadastroMedico;
import br.com.magnasistemas.api_saude.dto.medico.DadosDetalhamentoMedico;
import br.com.magnasistemas.api_saude.entity.Especialidade;
import br.com.magnasistemas.api_saude.entity.Medico;
import br.com.magnasistemas.api_saude.repository.EspecialidadeRepository;
import br.com.magnasistemas.api_saude.repository.MedicoRepository;
import jakarta.validation.Valid;

@Service
public class MedicoService {

	static final String MENSAGEMID = "O ID passado não existe!";
	
	@Autowired
	MedicoRepository medicoRepository;
	
	@Autowired
	EspecialidadeRepository especialidadeRepository;
	
	public DadosDetalhamentoMedico cadastro (@Valid DadosCadastroMedico dados){
		if(crmExiste(dados.crm())) {
//			return ResponseEntity.status(409).build();
		}
		
		if(!especialidadeExiste(dados.especialidades())) {
//			return ResponseEntity.notFound().build();
		}
		
		Medico medico =  new Medico(dados);	
		
		medicoRepository.save(medico);
		
		for (Long especialidade : dados.especialidades()) {
			medicoRepository.cadastrarMedEsp(medico.getId(), especialidade);
		}
		
		List<DadosDetalhamentoEspecialidade> listEsp = new ArrayList<>();
		
		for (Long especialidade : dados.especialidades()) {
			listEsp.add(new DadosDetalhamentoEspecialidade(especialidadeRepository.getReferenceById(especialidade)));
		}
		
		return new DadosDetalhamentoMedico(medico, listEsp);
	}
	
	public Page<DadosDetalhamentoMedico> listar(Pageable pageable){
		Page<Medico> pageMedicos = medicoRepository.findAll(pageable);
		
		return pageMedicos.map(medico ->{
			List<DadosDetalhamentoEspecialidade> listEsp = new ArrayList<>();
			
			for (Especialidade especialidade : medico.getEspecialidades()) {
				listEsp.add(new DadosDetalhamentoEspecialidade(especialidade));
			}
			
			return new DadosDetalhamentoMedico(medico, listEsp);
		});
	}
	
	public DadosDetalhamentoMedico atualizar (@Valid DadosAtualizarMedico dados){
		if(!medicoRepository.existsById(dados.id())) {
//			return ResponseEntity.notFound().build();
		}
		
		Medico medico = medicoRepository.getReferenceById(dados.id());
		
		medico.setNome(dados.nome());
		
		if(crmExiste(dados.crm()) && !dados.crm().equals(medico.getCrm())) {
//			return ResponseEntity.status(409).build();
		}
		
		medico.setCrm(dados.crm());
		
		List<DadosDetalhamentoEspecialidade> listEsp = new ArrayList<>();
		
		for (Especialidade especialidade : medico.getEspecialidades()) {
			listEsp.add(new DadosDetalhamentoEspecialidade(especialidade));
		}
		
		return new DadosDetalhamentoMedico(medico, listEsp);
	}
	
	public void excluir(Long id){
		if(!medicoRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
		}
		
		medicoRepository.deleteById(id);
	
	}
	
	public DadosDetalhamentoMedico detalhar(Long id){
		if(!medicoRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
		}
		
		Medico medico = medicoRepository.getReferenceById(id);
		
		List<DadosDetalhamentoEspecialidade> listEsp = new ArrayList<>();
		
		for (Especialidade especialidade : medico.getEspecialidades()) {
			listEsp.add(new DadosDetalhamentoEspecialidade(especialidade));
		}
		return new DadosDetalhamentoMedico(medico, listEsp);
	}
	
	
	private boolean crmExiste(String crm) {
		return  medicoRepository.existsByCrm(crm);
	}
	
	private boolean especialidadeExiste(List<Long> listaEspecialidades) {
		for (Long especialidade : listaEspecialidades) {
			if(!especialidadeRepository.existsById(especialidade)) {
				return false;
			}
		}
		return true;
	}
}
