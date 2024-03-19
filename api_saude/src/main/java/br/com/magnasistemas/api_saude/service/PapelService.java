package br.com.magnasistemas.api_saude.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.api_saude.dto.papel.DadosDetalhamentoPapel;
import br.com.magnasistemas.api_saude.entity.Papel;
import br.com.magnasistemas.api_saude.repository.PapelRepository;

@Service
public class PapelService {

	@Autowired
	PapelRepository papelRepository;
	
	public List<DadosDetalhamentoPapel> listar(){
		List<Papel> listaPapeis = papelRepository.findAll();
		
		List<DadosDetalhamentoPapel> listDet = new ArrayList<>();
		
		for (Papel papel : listaPapeis) {
			listDet.add(new DadosDetalhamentoPapel(papel));
		}
		
		return listDet;
	}
}
