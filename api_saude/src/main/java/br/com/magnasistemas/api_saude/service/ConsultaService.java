package br.com.magnasistemas.api_saude.service;


import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.api_saude.dto.consulta.DadosAtualizarConsulta;
import br.com.magnasistemas.api_saude.dto.consulta.DadosCadastroConsulta;
import br.com.magnasistemas.api_saude.dto.consulta.DadosDetalhamentoConsulta;
import br.com.magnasistemas.api_saude.entity.Consulta;
import br.com.magnasistemas.api_saude.repository.ConsultaRepository;
import br.com.magnasistemas.api_saude.repository.EspecialidadeRepository;
import br.com.magnasistemas.api_saude.repository.MedicoRepository;
import br.com.magnasistemas.api_saude.repository.PacienteRepository;
import br.com.magnasistemas.api_saude.validators.implementers.consulta.ValidadorConsultaAtualizacao;
import br.com.magnasistemas.api_saude.validators.implementers.consulta.ValidadorConsultaCadastro;
import br.com.magnasistemas.api_saude.validators.implementers.consulta.ValidadorConsultaExistencia;
import jakarta.validation.Valid;

@Service
public class ConsultaService {

	static final String MENSAGEMID = "O ID passado não existe!";
	
	@Autowired
	ConsultaRepository consultaRepository;
	
	@Autowired
	MedicoRepository medicoRepository;
	
	@Autowired
	EspecialidadeRepository especialidadeRepository;
	
	@Autowired
	ValidadorConsultaAtualizacao validadorAtualizacao;
	
	@Autowired
	ValidadorConsultaCadastro validadorCadastro;
	
	@Autowired
	ValidadorConsultaExistencia validorExistencia;
	
	@Autowired
	PacienteRepository pacienteRepository;
	
	public DadosDetalhamentoConsulta cadastro (@Valid DadosCadastroConsulta dados){
		
		Instant dataHoraCorreta = dados.dataHora().toInstant().plus(3, ChronoUnit.HOURS);
		Timestamp dataHoraTimestamp = Timestamp.from(dataHoraCorreta);
		DadosCadastroConsulta dadosHoraCorreta = new DadosCadastroConsulta(dados.idPaciente(), dados.idMedico(), dados.idEspecialidade(), dataHoraTimestamp);
		
		validadorCadastro.validador(dadosHoraCorreta);
		
		
		Consulta consulta = new Consulta(
										pacienteRepository.getReferenceById(dadosHoraCorreta.idPaciente()),
										medicoRepository.getReferenceById(dadosHoraCorreta.idMedico()),
										especialidadeRepository.getReferenceById(dadosHoraCorreta.idEspecialidade()),
										dadosHoraCorreta.dataHora()
										);
		
		consultaRepository.save(consulta);
		
		return new DadosDetalhamentoConsulta(consulta);
	}
	
	public List<DadosDetalhamentoConsulta> listar(){
		List<Consulta> listConsulta = consultaRepository.findAll();
		
		List<DadosDetalhamentoConsulta> listDet = new ArrayList<>();
		
		for (Consulta consulta : listConsulta) {
			listDet.add(new DadosDetalhamentoConsulta(consulta));
		}
		
		return listDet;
	}
	
	public DadosDetalhamentoConsulta atualizar (@Valid DadosAtualizarConsulta dados){
		validadorAtualizacao.validador(dados);
		
		Consulta consulta = consultaRepository.getReferenceById(dados.id());
		
		consulta.setFkPaciente(pacienteRepository.getReferenceById(dados.idPaciente()));
		consulta.setFkMedico(medicoRepository.getReferenceById(dados.idMedico()));
		consulta.setFkEspecialidade(especialidadeRepository.getReferenceById(dados.idEspecialidade()));
		
		Instant dataHoraCorreta = dados.dataHora().toInstant().plus(3, ChronoUnit.HOURS);
		Timestamp dataHoraTimestamp = Timestamp.from(dataHoraCorreta);
		
		consulta.setDataHora(dataHoraTimestamp);
		
		return new DadosDetalhamentoConsulta(consulta);
	}
	
	public void excluir(Long id){
		validorExistencia.validador(id);
		
		consultaRepository.deleteById(id);
	}
	
	public DadosDetalhamentoConsulta detalhar(Long id){
		validorExistencia.validador(id);
		
		Consulta consulta = consultaRepository.getReferenceById(id);
		
		return new DadosDetalhamentoConsulta(consulta);
	}
	
	public List<DadosDetalhamentoConsulta> listarPorCpf(String cpf){
		List<Consulta> listaConsulta = consultaRepository.listaPorCpf("%"+cpf+"%");
		
		List<DadosDetalhamentoConsulta> listDet = new ArrayList<>();
		
		for (Consulta consulta : listaConsulta) {
			listDet.add(new DadosDetalhamentoConsulta(consulta));	
		}
		
		return listDet;
	}
	
	public List<DadosDetalhamentoConsulta> listarPorIdCliente(Long id){
		
		List<Consulta> listaConsulta = consultaRepository.listarPorIdPaciente(id);
		
		List<DadosDetalhamentoConsulta> listDet = new ArrayList<>();
		
		for (Consulta consulta : listaConsulta) {
			listDet.add(new DadosDetalhamentoConsulta(consulta));
		}
		
		return listDet;
	}
}
