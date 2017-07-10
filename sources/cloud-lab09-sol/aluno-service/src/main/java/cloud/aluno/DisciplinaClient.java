package cloud.aluno;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "disciplina-service")
public interface DisciplinaClient {

	@RequestMapping(value = "/disciplinas", method = RequestMethod.GET)
    Resources<DisciplinaDTO> getAllDisciplinas();

    @RequestMapping(value = "/disciplinas/{id}/dto", method = RequestMethod.GET)
    DisciplinaDTO getDisciplina(@PathVariable("id") Long id);
	
}
