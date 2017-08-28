package boot.lab04;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping("/alunos")
public class AlunoRestController {

	@Autowired
	AlunoRepository repository;
	
	AlunoResourceAssembler assembler = new AlunoResourceAssembler();
	
	@PostConstruct
	public void init() {
		repository.save(new Aluno(1l, "John", 11111, "john@john.com", new Date()));
		repository.save(new Aluno(2l, "Steve", 22222, "steve.stevent@st.com", new Date()));
		repository.save(new Aluno(3l, "Mary", 33333, "mary@robinson.com", new Date()));
		repository.save(new Aluno(4l, "Kate", 44444,"kate@kate.com", new Date()));
		repository.save(new Aluno(5l, "Diana", 55555,"diana@doll.com", new Date()));		
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<AlunoResource>> getAll() {
		return new ResponseEntity<>(assembler.toResources(repository.findAll()), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AlunoResource> get(@PathVariable Long id) {
		Aluno aluno = repository.findOne(id);
		if (aluno != null) {			
			return new ResponseEntity<>(assembler.toResource(aluno), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	public ResponseEntity<AlunoResource> create(@RequestBody Aluno aluno) {
		aluno = repository.save(aluno);
		if (aluno != null) {
			return new ResponseEntity<>(assembler.toResource(aluno), HttpStatus.OK);					
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AlunoResource> update(@PathVariable Long id, @RequestBody Aluno aluno) {
		if (aluno != null) {
			aluno.setId(id);
			aluno = repository.save(aluno);
			return new ResponseEntity<>(assembler.toResource(aluno), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<AlunoResource> delete(@PathVariable Long id) {
		Aluno aluno = repository.findOne(id);
		if (aluno != null) {
			repository.delete(aluno);
			return new ResponseEntity<>(assembler.toResource(aluno), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<AlunoResource>> findByNome(@PathVariable String nome) {
		return new ResponseEntity<>(assembler.toResources(repository.findByNomeContaining(nome)), HttpStatus.OK);
	}
	
	@GetMapping("/nascimento/mes/corrente")
	public ResponseEntity<List<AlunoResource>> findByDataNascimentoAtMesCorrente() {
		int mesCorrente = Calendar.getInstance().get(Calendar.MONTH) + 1;
		return new ResponseEntity<>(
				assembler.toResources(repository.findByDataNascimentoAtMesCorrente(mesCorrente)), 
				HttpStatus.OK);
	}
}
