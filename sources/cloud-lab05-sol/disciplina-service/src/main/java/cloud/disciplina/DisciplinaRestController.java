package cloud.disciplina;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaRestController {

	@Autowired
	DisciplinaRepository repository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/nomes")
	public List<String> getDisciplinas() {
		return repository.findAll()
				.stream().map(d -> d.getNome()).collect(Collectors.toList());
	}
	
	@GetMapping("/{id}/dto")
	@SuppressWarnings("all")
	public DisciplinaDTO getDisciplina(@PathVariable Long id) throws Exception {
		
		ResponseEntity<List> alunos = restTemplate.getForEntity(
				"http://aluno-service/alunos/nomes",
				List.class);
		
		Disciplina disciplina = repository.findOne(id);
	
		return DisciplinaDTO.builder()
				.id(disciplina.getId())
				.nome(disciplina.getNome())
				.cargaHoraria(disciplina.getCargaHoraria())
				.dataInicio(disciplina.getDataInicio())
				.alunosMatriculados((List<String>) alunos.getBody())
				.build();
	}		
	
}