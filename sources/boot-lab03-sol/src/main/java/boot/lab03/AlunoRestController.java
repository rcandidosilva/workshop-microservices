package boot.lab03;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

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

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/alunos")
public class AlunoRestController {

	List<Aluno> list = new ArrayList<>();
	
	AlunoResourceAssembler assembler = new AlunoResourceAssembler();
	
	@PostConstruct
	public void init() {
		list.add(new Aluno(1l, "John", 11111, "john@john.com"));
		list.add(new Aluno(2l, "Steve", 22222, "steve.stevent@st.com"));
		list.add(new Aluno(3l, "Mary", 33333, "mary@robinson.com"));
		list.add(new Aluno(4l, "Kate", 44444,"kate@kate.com"));
		list.add(new Aluno(5l, "Diana", 55555,"diana@doll.com"));		
	}
	
	@ApiOperation("Retorna a lista de alunos")
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<AlunoResource>> getAll() {
		return new ResponseEntity<>(assembler.toResources(list), HttpStatus.OK);
	}
	
	@ApiOperation("Retorna as informações do aluno pelo id")
	@GetMapping("/{id}")
	public ResponseEntity<AlunoResource> get(@PathVariable Long id) {
		OptionalInt index = IntStream.range(0, list.size())
			     .filter(i -> list.get(i).getId().equals(id)).findFirst();
		if (index.isPresent()) {			
			Aluno aluno = list.get(index.getAsInt());
			return new ResponseEntity<>(assembler.toResource(aluno), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation("Cria um novo aluno")
	@PostMapping
	public ResponseEntity<AlunoResource> create(@RequestBody Aluno aluno) {
		if (list.add(aluno)) {
			return new ResponseEntity<>(assembler.toResource(aluno), HttpStatus.OK);					
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@ApiOperation("Atualiza as informações do aluno pelo id")
	@PutMapping("/{id}")
	public ResponseEntity<AlunoResource> update(@PathVariable Long id, @RequestBody Aluno aluno) {
		OptionalInt index = IntStream.range(0, list.size())
			     .filter(i -> list.get(i).getId().equals(id)).findFirst();
		if (index.isPresent()) {
			list.add(aluno);
			return new ResponseEntity<>(assembler.toResource(aluno), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@ApiOperation("Remove um determinado aluno pelo id")
	@DeleteMapping("/{id}")
	public ResponseEntity<AlunoResource> delete(@PathVariable Long id) {
		OptionalInt index = IntStream.range(0, list.size()).filter(i -> list.get(i).getId().equals(id)).findFirst();
		if (index.isPresent()) {
			Aluno aluno = list.remove(index.getAsInt());
			return new ResponseEntity<>(assembler.toResource(aluno), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
