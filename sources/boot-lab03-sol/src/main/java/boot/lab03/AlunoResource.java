package boot.lab03;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

@XmlRootElement
@XmlSeeAlso({Aluno.class})
public class AlunoResource extends Resource<Aluno> {
	
	public AlunoResource() {
		this(new Aluno());
	}
	
	public AlunoResource(Aluno aluno, Link... links) {
		super(aluno, links);
	}
	
}