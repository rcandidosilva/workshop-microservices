package boot.lab07;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuditoriaRepositoryTest {

	@Autowired
	AuditoriaRepository repository;
	
	@Test
	public void testSaveAuditoria() throws Exception {
		Auditoria auditoria = Auditoria.builder()
				.acao("processSaldo").usuario("Maria")
				.timestamp(new Date()).build();
		
		auditoria = repository.save(auditoria);
		
		assertNotNull(auditoria);
		assertTrue(auditoria.getUuid() != null);
	}	
	
	@Test
	public void testDeleteAuditoria() throws Exception {
		Auditoria auditoria = Auditoria.builder()
				.acao("processSaldo").usuario("Joao")
				.timestamp(new Date()).build();
		
		auditoria = repository.save(auditoria);
		repository.delete(auditoria);
		
		auditoria = repository.findOne(auditoria.getUuid());
		
		assertNull(auditoria);
	}
	
	@Test
	public void testFindByUsuario() throws Exception {
		Auditoria auditoria = Auditoria.builder()
				.acao("processSaldo").usuario("Rodrigo")
				.timestamp(new Date()).build();
		
		auditoria = repository.save(auditoria);
		
		List<Auditoria> list = repository.findByUsuario("Rodrigo");
		
		assertNotNull(list);
		assertFalse(list.isEmpty());
		assertTrue(list.get(0).getUsuario().equals("Rodrigo"));
	}

}