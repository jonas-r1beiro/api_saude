package br.com.magnasistemas.api_saude.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.magnasistemas.api_saude.entity.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
	
	boolean existsByCpf(String cpf);
	
	List<Paciente> findByCpfContainingIgnoreCase(String cpf);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM TB_PACIENTE; ALTER SEQUENCE TB_PACIENTE_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndReseteSequence();
}
