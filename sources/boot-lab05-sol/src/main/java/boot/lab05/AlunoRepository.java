package boot.lab05;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface AlunoRepository extends JpaRepository<Aluno, Long> {	

	List<Aluno> findByNomeContaining(String nome);
	
	@Query("SELECT a FROM Aluno a WHERE MONTH(a.dataNascimento) = :mes")
	List<Aluno> findByDataNascimentoAtMesCorrente(@Param("mes") Integer mes);
	
}
