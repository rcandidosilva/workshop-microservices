package boot.lab10;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface AlunoRepository extends JpaRepository<Aluno, Long> {	

	@Cacheable("alunosByNome")
	List<Aluno> findByNomeContaining(String nome);
	
	@Cacheable("alunosByDataNascimento")
	@Query("SELECT a FROM Aluno a WHERE MONTH(a.dataNascimento) = :mes")
	List<Aluno> findByDataNascimentoAtMesCorrente(@Param("mes") Integer mes);
	
}
