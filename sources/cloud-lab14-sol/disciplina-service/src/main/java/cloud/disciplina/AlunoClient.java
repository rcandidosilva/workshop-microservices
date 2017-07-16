package cloud.disciplina;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "aluno-service", fallback = AlunoClientFallback.class)
public interface AlunoClient {

	@RequestMapping(value = "/alunos", method = RequestMethod.GET)
    Resources<AlunoDTO> getAllAlunos();

    @RequestMapping(value = "/alunos/{id}/dto", method = RequestMethod.GET)
    AlunoDTO getAluno(@PathVariable("id") Long id);
	
}