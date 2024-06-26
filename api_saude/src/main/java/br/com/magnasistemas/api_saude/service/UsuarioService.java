package br.com.magnasistemas.api_saude.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.api_saude.dto.usuario.DadosAtualizarUsuario;
import br.com.magnasistemas.api_saude.dto.usuario.DadosCadastroUsuario;
import br.com.magnasistemas.api_saude.dto.usuario.DadosDetalhamentoUsuario;
import br.com.magnasistemas.api_saude.entity.Usuario;
import br.com.magnasistemas.api_saude.repository.PapelRepository;
import br.com.magnasistemas.api_saude.repository.UsuarioRepository;
import br.com.magnasistemas.api_saude.validators.implementers.usuario.ValidadorUsuarioAtualizacao;
import br.com.magnasistemas.api_saude.validators.implementers.usuario.ValidadorUsuarioCadastro;
import br.com.magnasistemas.api_saude.validators.implementers.usuario.ValidadorUsuarioExistencia;
import jakarta.validation.Valid;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	PapelRepository papelRepository;
	
	@Autowired
	ValidadorUsuarioCadastro validadorCadastro;
	
	@Autowired
	ValidadorUsuarioAtualizacao validadorAtualizacao;
	
	@Autowired
	ValidadorUsuarioExistencia validadorExistencia;
	
	public DadosDetalhamentoUsuario cadastro(DadosCadastroUsuario dados) {
		validadorCadastro.validador(dados);
		
		String senhaCrip = criarSenha(dados.senha());
		
		Usuario usuario = new Usuario(dados.login(), senhaCrip, dados.idExterno(), papelRepository.getReferenceById(dados.idPapel()));
		
		usuarioRepository.save(usuario);
		
		return new DadosDetalhamentoUsuario(usuario);
	}
	
	public List<DadosDetalhamentoUsuario> listar(){
		List<Usuario> listaUsuarios = usuarioRepository.findAll();
		
		List<DadosDetalhamentoUsuario> listDet = new ArrayList<>();
		
		for (Usuario usuario : listaUsuarios) {
			listDet.add(new DadosDetalhamentoUsuario(usuario));
		}
		
		return listDet;
	}
	
	public DadosDetalhamentoUsuario atualizar (@Valid DadosAtualizarUsuario dados){
		validadorAtualizacao.validador(dados);
		
		Usuario usuario = usuarioRepository.getReferenceById(dados.id());
		
		usuario.setIdExterno(dados.idExterno());
		usuario.setLogin(dados.login());
		usuario.setSenha(criarSenha(dados.senha()));
		usuario.setPapel(papelRepository.getReferenceById(dados.idPapel()));
		
		return new DadosDetalhamentoUsuario(usuario);
	}
	
	public void excluir(Long id){
		validadorExistencia.validador(id);
		
		usuarioRepository.deleteById(id);
	}
	
	public DadosDetalhamentoUsuario detalhar(Long id){
		validadorExistencia.validador(id);
		
		Usuario usuario = usuarioRepository.getReferenceById(id);
		
		return new DadosDetalhamentoUsuario(usuario);
	}
	
	
	
	private String criarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
		
		return encoder.encode(senha);
	}

}
