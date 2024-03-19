package br.com.magnasistemas.api_saude.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.api_saude.dto.especialidade.DadosAtualizarEspecialidade;
import br.com.magnasistemas.api_saude.dto.especialidade.DadosCadastroEspecialidade;
import br.com.magnasistemas.api_saude.dto.especialidade.DadosDetalhamentoEspecialidade;
import br.com.magnasistemas.api_saude.entity.Especialidade;
import br.com.magnasistemas.api_saude.repository.EspecialidadeRepository;
import br.com.magnasistemas.api_saude.validators.implementers.especialidade.ValidadorEspecialidadeAtualizacao;
import br.com.magnasistemas.api_saude.validators.implementers.especialidade.ValidadorEspecialidadeCadastro;
import br.com.magnasistemas.api_saude.validators.implementers.especialidade.ValidadorEspecialidadeDelete;
import br.com.magnasistemas.api_saude.validators.implementers.especialidade.ValidadorEspecialidadeDetalhar;
import jakarta.validation.Valid;

@Service
public class EspecialidadeService {
	
	static final String MENSAGEMID = "O ID passado não existe!";
	
	@Autowired
	EspecialidadeRepository especialidadeRepository;
	
	@Autowired
	ValidadorEspecialidadeAtualizacao validadorAtualizacao;
	
	@Autowired
	ValidadorEspecialidadeCadastro validadorCadastro;
	
	@Autowired
	ValidadorEspecialidadeDelete validadorDelete;
	
	@Autowired
	ValidadorEspecialidadeDetalhar validadorDetalhar;

	public DadosDetalhamentoEspecialidade cadastro (@Valid DadosCadastroEspecialidade dados){
		validadorCadastro.validador(dados);
		
		Especialidade especialidade =  new Especialidade(dados);	
		
		especialidadeRepository.save(especialidade);
		
		return new DadosDetalhamentoEspecialidade(especialidade);
	}
	
	public List<DadosDetalhamentoEspecialidade> listar(){
		List<Especialidade> listEspecialidades = especialidadeRepository.findAll();
		
		List<DadosDetalhamentoEspecialidade> listDet = new ArrayList<>();
		
		for (Especialidade especialidade : listEspecialidades) {
			listDet.add(new DadosDetalhamentoEspecialidade(especialidade));
		}
		
		return listDet;
	}
	
	public DadosDetalhamentoEspecialidade atualizar (@Valid DadosAtualizarEspecialidade dados){
		validadorAtualizacao.validador(dados);
		
		Especialidade especialidade = especialidadeRepository.getReferenceById(dados.id());
		
		especialidade.setNome(dados.nome());
		
		return new DadosDetalhamentoEspecialidade(especialidade);
	}
	
	public void excluir(Long id){
		validadorDelete.validador(id);
		
		especialidadeRepository.deleteById(id);
	}
	
	public DadosDetalhamentoEspecialidade detalhar(Long id){
		validadorDetalhar.validador(id);
		
		Especialidade especialidade = especialidadeRepository.getReferenceById(id);
		
		return new DadosDetalhamentoEspecialidade(especialidade);
	}
}
