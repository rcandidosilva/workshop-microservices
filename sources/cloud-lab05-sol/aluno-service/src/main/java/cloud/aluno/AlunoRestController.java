package cloud.aluno;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/alunos")
@RibbonClient(name = "disciplina-service", 
	configuration = RibbonConfiguration.class)
public class AlunoRestController {

	@Autowired
	AlunoRepository repository;

    @LoadBalanced @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping
	public List<String> getAlunos() {
		return repository.findAll()
				.stream()
				.map(a -> a.getNome())
				.collect(Collectors.toList());
	}
		

	@GetMapping("/{id}")
	@SuppressWarnings("all")
	public AlunoDTO getAluno(@PathVariable Long id) throws Exception {

		ResponseEntity<List> disciplinas = restTemplate.getForEntity(
				"http://disciplina-service/disciplinas",
				List.class);
		
		Aluno aluno = repository.findOne(id);

		return AlunoDTO.builder().id(aluno.getId())
				.nome(aluno.getNome())
				.disciplinas((List<String>) disciplinas.getBody())
				.email(aluno.getEmail()).build();
	}

}