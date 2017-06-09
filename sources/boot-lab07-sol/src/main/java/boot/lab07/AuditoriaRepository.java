package boot.lab07;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AuditoriaRepository extends MongoRepository<Auditoria, String> {

	List<Auditoria> findByTimestampAfter(Date timestamp);
	
	@Query("{ 'usuario' : ?0 }")
	List<Auditoria> findByUsuario(String usuario);
	
}