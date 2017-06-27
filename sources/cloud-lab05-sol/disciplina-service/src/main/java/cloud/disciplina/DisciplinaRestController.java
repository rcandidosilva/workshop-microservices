package cloud.disciplina;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
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
	
//	@Autowired 
//	DiscoveryClient discoveryClient;
	
//	@Autowired
//	ObjectMapper objectMapper;
	
	@Autowired
	RestTemplate restTemplate;
	
    @LoadBalanced @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }	
	
	@GetMapping
	public List<String> getDisciplinas() {
		return repository.findAll()
				.stream()
				.map(d -> d.getNome())
				.collect(Collectors.toList());
	}
	
//	@GetMapping("/{id}")
//	@SuppressWarnings("all")
//	public DisciplinaDTO getDisciplina(@PathVariable Long id) throws Exception {
//		
//		List<ServiceInstance> alunosService = discoveryClient.getInstances("aluno-service");
//		ServiceInstance instance = alunosService.get(0);
//		
//		HttpGet getRequest = new HttpGet(instance.getUri() + "/alunos");
//		HttpClientBuilder builder = HttpClientBuilder.create();
//		HttpClient client = builder.build();
//		HttpResponse response = client.execute(getRequest);
//		String content = EntityUtils.toString(response.getEntity(), "UTF-8");
//		Map mapContent = objectMapper.readValue(content, HashMap.class);
//		List<Map> alunos = (List) PropertyUtils.getProperty(mapContent, "_embedded.alunos");
//		List<String> nomes = alunos.stream().map(m -> m.get("nome").toString()).collect(Collectors.toList());
//		
//		Disciplina disciplina = repository.findOne(id);
//	
//		return DisciplinaDTO.builder()
//				.id(disciplina.getId())
//				.nome(disciplina.getNome())
//				.cargaHoraria(disciplina.getCargaHoraria())
//				.dataInicio(disciplina.getDataInicio())
//				.alunosMatriculados(nomes).build();
//	}
	
	@GetMapping("/{id}")
	@SuppressWarnings("all")
	public DisciplinaDTO getDisciplina(@PathVariable Long id) throws Exception {
		
		ResponseEntity<List> disciplinas = restTemplate.getForEntity(
				"http://aluno-service/alunos",
				List.class);
		
		Disciplina disciplina = repository.findOne(id);
	
		return DisciplinaDTO.builder()
				.id(disciplina.getId())
				.nome(disciplina.getNome())
				.cargaHoraria(disciplina.getCargaHoraria())
				.dataInicio(disciplina.getDataInicio())
				.alunosMatriculados((List<String>) disciplinas.getBody()).build();
	}	
	
}
