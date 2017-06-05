package boot.lab06;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AlunoRepositoryTest {

	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	AlunoRepository repository;
	
	@Test
	public void testSaveAluno() throws Exception {
		Aluno aluno = Aluno.builder()
		.nome("Rodrigo").matricula(67676)
		.email("rodrigo@email.com").dataNascimento(new Date()).build();
		
		aluno = repository.save(aluno);
		
		assertNotNull(aluno);
		assertTrue(aluno.getId() != null);
	}
	

}
