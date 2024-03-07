package br.com.magnasistemas.api_saude.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.magnasistemas.api_saude.entity.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

	boolean existsByCrm(String crm);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO tb_medico_especialidade (fk_medico, fk_especialidade) VALUES (:fkMedico, :fkEspecialidade)", nativeQuery = true)
	void cadastrarMedEsp(@Param("fkMedico") Long fkMedico, @Param("fkEspecialidade") Long fkEspecialidade);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM TB_MEDICO; ALTER SEQUENCE TB_MEDICO_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndReseteSequence();
}
