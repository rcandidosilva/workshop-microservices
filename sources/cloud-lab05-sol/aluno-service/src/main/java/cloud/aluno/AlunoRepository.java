package cloud.aluno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "/alunos", collectionResourceRel = "alunos")
public interface AlunoRepository extends JpaRepository<Aluno, Long>{
	
}