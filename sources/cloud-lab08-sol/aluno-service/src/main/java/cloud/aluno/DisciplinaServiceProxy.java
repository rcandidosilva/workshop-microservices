package cloud.aluno;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class DisciplinaServiceProxy {
	
	@Autowired
	DisciplinaClient disciplinaClient;
	
	@HystrixCommand(fallbackMethod = "getNomesDisciplinasFallback")
	List<String> getNomesDisciplinas() {
		Resources<DisciplinaDTO> disciplinas = disciplinaClient.getAllDisciplinas();
		return disciplinas.getContent().stream()
				.map(d -> d.getNome()).collect(Collectors.toList());
	}
	
	List<String> getNomesDisciplinasFallback() {
		return new ArrayList<>();
	}

}