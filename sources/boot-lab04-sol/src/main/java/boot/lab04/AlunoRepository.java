package boot.lab04;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {	

	List<Aluno> findByNomeContaining(String nome);
	
	@Query("SELECT a FROM Aluno a WHERE MONTH(a.dataNascimento) = :mes")
	List<Aluno> findByDataNascimentoAtMesCorrente(@Param("mes") Integer mes);
	
}
