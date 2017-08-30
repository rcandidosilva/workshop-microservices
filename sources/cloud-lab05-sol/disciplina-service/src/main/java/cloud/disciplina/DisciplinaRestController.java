package cloud.disciplina;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaRestController {

	@Autowired
	DisciplinaRepository repository;
	
	@Autowired
	AlunoClient alunoClient;
	
	@GetMapping("/nomes")
	public List<String> getDisciplinas() {
		return repository.findAll()
				.stream().map(d -> d.getNome()).collect(Collectors.toList());
	}
	
	@GetMapping("/{id}/dto")
	@SuppressWarnings("all")
	public DisciplinaDTO getDisciplina(@PathVariable Long id) throws Exception {
		
		Resources<AlunoDTO> alunos = alunoClient.getAllAlunos();
		List<String> alunosNomes = alunos.getContent().stream()
				.map(a -> a.getNome()).collect(Collectors.toList());
		
		Disciplina disciplina = repository.findOne(id);
	
		return DisciplinaDTO.builder()
				.id(disciplina.getId())
				.nome(disciplina.getNome())
				.cargaHoraria(disciplina.getCargaHoraria())
				.dataInicio(disciplina.getDataInicio())
				.alunosMatriculados(alunosNomes)
				.build();
	}		
	
}