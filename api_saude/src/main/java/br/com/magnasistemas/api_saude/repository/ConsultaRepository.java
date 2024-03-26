package br.com.magnasistemas.api_saude.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.magnasistemas.api_saude.entity.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
	
	@Query(value = "SELECT tc.id, tc.fk_paciente, tc.fk_medico, tc.fk_especialidade, tc.data_hora from tb_consulta tc INNER JOIN tb_paciente tp on tc.fk_paciente = tp.id WHERE tp.id = :id", nativeQuery = true)
	List<Consulta> listarPorIdPaciente(@Param("id") Long id);
	
	@Query(value = "SELECT tc.id, tc.fk_paciente, tc.fk_medico, tc.fk_especialidade, tc.data_hora from tb_consulta tc INNER JOIN tb_paciente tp on tc.fk_paciente = tp.id WHERE tp.cpf LIKE :cpf OR tp.cpf LIKE :cpfCompleto", nativeQuery = true)
	List<Consulta> listaPorCpf(@Param("cpf") String cpf, @Param("cpfCompleto") String cpfCompleto);
	
	@Query(value = "SELECT * FROM tb_consulta WHERE fk_paciente = :idPaciente AND DATE(data_hora) = :dataDaConsulta", nativeQuery = true)
	List<Consulta> consultaPorDia(@Param("idPaciente") Long idPaciente, @Param("dataDaConsulta") Timestamp dataHora);

	@Query(value = "SELECT tc.id, tc.fk_paciente, tc.fk_medico, tc.fk_especialidade, tc.data_hora FROM tb_consulta tc INNER JOIN tb_medico tm ON tc.fk_medico = tm.id INNER JOIN tb_especialidade te ON tc.fk_especialidade = te.id WHERE :dataHoraConsulta >= tc.data_hora AND :dataHoraConsulta <= tc.data_hora + INTERVAL '1 hour' AND tm.id = :idMedico", nativeQuery = true)
	List<Consulta> horarioMedico(@Param("idMedico") Long idMedico, @Param("dataHoraConsulta") Timestamp dataHora);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM TB_CONSULTA; ALTER SEQUENCE TB_CONSULTA_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndReseteSequence();
}
