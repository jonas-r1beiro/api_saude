package br.com.magnasistemas.api_saude.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.magnasistemas.api_saude.entity.MedicoEspecialidade;

public interface MedEspRepository extends JpaRepository<MedicoEspecialidade, Long> {

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM TB_MEDICO_ESPECIALIDADE; ALTER SEQUENCE TB_MEDICO_ESPECIALIDADE_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndReseteSequence();
}