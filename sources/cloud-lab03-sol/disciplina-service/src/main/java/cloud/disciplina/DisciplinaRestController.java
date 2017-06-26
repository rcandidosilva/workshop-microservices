package cloud.disciplina;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaRestController {

	@Autowired 
	DiscoveryClient discoveryClient;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@GetMapping
	@SuppressWarnings("all")
	public DisciplinaDTO getDisciplina() throws Exception {
		List<ServiceInstance> alunosService = discoveryClient.getInstances("aluno-service");
		ServiceInstance instance = alunosService.get(0);
		
		HttpGet getRequest = new HttpGet(instance.getUri() + "/alunos");
		HttpClientBuilder builder = HttpClientBuilder.create();
		HttpClient client = builder.build();
		HttpResponse response = client.execute(getRequest);
		String content = EntityUtils.toString(response.getEntity(), "UTF-8");
		Map mapContent = objectMapper.readValue(content, HashMap.class);
		List<Map> alunos = (List) PropertyUtils.getProperty(mapContent, "_embedded.alunos");
		List<String> nomes = alunos.stream()
				.map(m -> m.get("nome").toString())
				.collect(Collectors.toList());
		
		return DisciplinaDTO.builder()
				.nome("Microservices")
				.cargaHoraria(80)
				.dataInicio(new Date())
				.alunosMatriculados(nomes).build();
	}
	
}
