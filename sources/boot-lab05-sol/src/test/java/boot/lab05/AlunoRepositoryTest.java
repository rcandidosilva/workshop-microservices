package boot.lab05;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import boot.lab05.Aluno;
import boot.lab05.AlunoRepository;

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
	
	@Test
	public void testDeleteAluno() throws Exception {
		Aluno aluno = entityManager.persist(Aluno.builder()
				.nome("Rodrigo").matricula(67676)
				.email("rodrigo@email.com")
				.dataNascimento(new Date()).build());
		
		repository.delete(aluno);		
		aluno = repository.findOne(aluno.getId());
		
		assertNull(aluno);
	}
	
	@Test
	public void testFindByNome() throws Exception {
		entityManager.persistAndFlush(Aluno.builder()
				.nome("Rodrigo").matricula(67676)
				.email("rodrigo@email.com")
				.dataNascimento(new Date()).build());
		
		List<Aluno> alunos = repository.findByNomeContaining("Rodrigo");
		
		assertNotNull(alunos);
		assertFalse(alunos.isEmpty());
		assertTrue(alunos.get(0).getNome().equals("Rodrigo"));
	}
	
	
	

}
