package br.com.magnasistemas.api_saude.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.api_saude.dto.especialidade.DadosDetalhamentoEspecialidade;
import br.com.magnasistemas.api_saude.dto.medico.DadosAtualizarMedico;
import br.com.magnasistemas.api_saude.dto.medico.DadosCadastroMedico;
import br.com.magnasistemas.api_saude.dto.medico.DadosDetalhamentoMedico;
import br.com.magnasistemas.api_saude.entity.Especialidade;
import br.com.magnasistemas.api_saude.entity.Medico;
import br.com.magnasistemas.api_saude.repository.EspecialidadeRepository;
import br.com.magnasistemas.api_saude.repository.MedicoRepository;
import br.com.magnasistemas.api_saude.validators.implementers.medico.ValidadorMedicoAtualizacao;
import br.com.magnasistemas.api_saude.validators.implementers.medico.ValidadorMedicoCadastro;
import br.com.magnasistemas.api_saude.validators.implementers.medico.ValidadorMedicoExistencia;
import jakarta.validation.Valid;

@Service
public class MedicoService {

	static final String MENSAGEMID = "O ID passado não existe!";
	
	@Autowired
	MedicoRepository medicoRepository;
	
	@Autowired
	EspecialidadeRepository especialidadeRepository;
	
	@Autowired
	ValidadorMedicoCadastro validadorCadastro;
	
	@Autowired
	ValidadorMedicoExistencia validadorExistencia;
	
	@Autowired
	ValidadorMedicoAtualizacao validadorAtualizacao;
	
	public DadosDetalhamentoMedico cadastro (@Valid DadosCadastroMedico dados){
		
		validadorCadastro.validador(dados);
		
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
		validadorExistencia.validador(dados.id());
		
		Medico medico = medicoRepository.getReferenceById(dados.id());
		
		medico.setNome(dados.nome());
		
		validadorAtualizacao.validador(dados);
		
		medico.setCrm(dados.crm());
		
		List<Especialidade> listEsp = new ArrayList<>();
		
		for (Long idEspecialidade : dados.especialidades()) {
			Especialidade esp = especialidadeRepository.getReferenceById(idEspecialidade);
			
			listEsp.add(esp);
		}
		
		medico.setEspecialidades(listEsp);
		
		List<DadosDetalhamentoEspecialidade> listEspDet = new ArrayList<>();
		for (Especialidade especialidade : medico.getEspecialidades()) {
			listEspDet.add(new DadosDetalhamentoEspecialidade(especialidade));
		}
		
		return new DadosDetalhamentoMedico(medico, listEspDet);
	}
	
	public void excluir(Long id){
		validadorExistencia.validador(id);
		
		medicoRepository.deleteById(id);
	
	}
	
	public DadosDetalhamentoMedico detalhar(Long id){
		validadorExistencia.validador(id);
		
		Medico medico = medicoRepository.getReferenceById(id);
		
		List<DadosDetalhamentoEspecialidade> listEsp = new ArrayList<>();
		
		for (Especialidade especialidade : medico.getEspecialidades()) {
			listEsp.add(new DadosDetalhamentoEspecialidade(especialidade));
		}
		
		return new DadosDetalhamentoMedico(medico, listEsp);
	}
}
