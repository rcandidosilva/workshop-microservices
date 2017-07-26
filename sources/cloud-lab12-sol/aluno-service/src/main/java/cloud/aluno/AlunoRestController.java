package cloud.aluno;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/alunos")
public class AlunoRestController {

	@Autowired
	AlunoRepository repository;
	
	@Autowired
	DisciplinaServiceProxy disciplinaProxy;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	DisciplinaClient disciplinaClient;
	
	@PreAuthorize("#oauth2.isUser()")
	@GetMapping("/nomes")
	public List<String> getAlunos() {
		return repository.findAll()
				.stream().map(a -> a.getNome()).collect(Collectors.toList());
	}

	@GetMapping("/alunos/{id}/disciplinas")
	public DisciplinaDTO getDisciplinasAlunos(@PathVariable Long id) {
		return disciplinaClient.getDisciplina(id);
	}

	
	@GetMapping("/{id}/dto")
	@SuppressWarnings("all")
	public AlunoDTO getAluno(@PathVariable Long id) throws Exception {

		List<String> nomesDisciplinas = disciplinaProxy.getNomesDisciplinas();
				
		Aluno aluno = repository.findOne(id);

		return AlunoDTO.builder().id(aluno.getId())
				.nome(aluno.getNome())
				.matricula(aluno.getMatricula())
				.email(aluno.getEmail())
				.disciplinas(nomesDisciplinas)
				.build();
	}

	
}