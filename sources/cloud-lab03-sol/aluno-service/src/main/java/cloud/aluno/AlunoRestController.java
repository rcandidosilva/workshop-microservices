package cloud.aluno;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/alunos")
@RibbonClient(name = "disciplina-service", configuration = RibbonConfiguration.class)
public class AlunoRestController {

	@Autowired
	AlunoRepository repository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/nomes")
	public List<String> getAlunos() {
		return repository.findAll()
				.stream().map(a -> a.getNome()).collect(Collectors.toList());
	}

	@GetMapping("/{id}/dto")
	@SuppressWarnings("all")
	public AlunoDTO getAluno(@PathVariable Long id) throws Exception {

		ResponseEntity<List> disciplinas = restTemplate.getForEntity(
				"http://disciplina-service/disciplinas/nomes",
				List.class);
		
		Aluno aluno = repository.findOne(id);

		return AlunoDTO.builder().id(aluno.getId())
				.nome(aluno.getNome())
				.matricula(aluno.getMatricula())
				.email(aluno.getEmail())
				.disciplinas((List<String>) disciplinas.getBody())
				.build();
	}

}