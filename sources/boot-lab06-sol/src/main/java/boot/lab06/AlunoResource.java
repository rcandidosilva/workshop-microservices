package boot.lab06;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class AlunoResource extends Resource<Aluno> {
	
	public AlunoResource(Aluno aluno, Link... links) {
		super(aluno, links);
	}
	
}