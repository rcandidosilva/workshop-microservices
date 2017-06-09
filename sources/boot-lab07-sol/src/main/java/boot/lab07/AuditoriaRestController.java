package boot.lab07;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auditoria")
public class AuditoriaRestController {

	@Autowired
	AuditoriaRepository repository;
	
	AuditoriaResourceAssembler assembler = new AuditoriaResourceAssembler();
		
	@GetMapping
	public ResponseEntity<List<AuditoriaResource>> getAll() {
		return new ResponseEntity<>(assembler.toResources(repository.findAll()), HttpStatus.OK);
	}
	
	@GetMapping("/{uuid}")
	public ResponseEntity<AuditoriaResource> get(@PathVariable String uuid) {
		Auditoria auditoria = repository.findOne(uuid);
		if (auditoria != null) {			
			return new ResponseEntity<>(assembler.toResource(auditoria), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	public ResponseEntity<AuditoriaResource> create(@RequestBody Auditoria auditoria) {
		auditoria = repository.save(auditoria);
		if (auditoria != null) {
			return new ResponseEntity<>(assembler.toResource(auditoria), HttpStatus.OK);					
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@PutMapping("/{uuid}")
	public ResponseEntity<AuditoriaResource> update(@PathVariable String uuid, @RequestBody Auditoria auditoria) {
		if (auditoria != null) {
			auditoria.setUuid(uuid);
			auditoria = repository.save(auditoria);
			return new ResponseEntity<>(assembler.toResource(auditoria), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@DeleteMapping("/{uuid}")
	public ResponseEntity<AuditoriaResource> delete(@PathVariable String uuid) {
		Auditoria auditoria = repository.findOne(uuid);
		if (auditoria != null) {
			repository.delete(auditoria);
			return new ResponseEntity<>(assembler.toResource(auditoria), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@GetMapping("/timestamp/{timestamp}")
	public ResponseEntity<List<AuditoriaResource>> findByTimestamp(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date timestamp) {
		return new ResponseEntity<>(assembler.toResources(repository.findByTimestampAfter(timestamp)), HttpStatus.OK);
	}
	
	@GetMapping("/usuario/{usuario}")
	public ResponseEntity<List<AuditoriaResource>> findByUsuario(@PathVariable String usuario) {
		return new ResponseEntity<>(assembler.toResources(repository.findByUsuario(usuario)), HttpStatus.OK);
	}
	
}