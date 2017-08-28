package boot.lab03;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class AlunoResourceAssembler extends ResourceAssemblerSupport<Aluno, AlunoResource> {
	
	public AlunoResourceAssembler() {
		super(Aluno.class, AlunoResource.class);
	}

	@Override
	public AlunoResource toResource(Aluno aluno) {
		return new AlunoResource(aluno, 
				linkTo(methodOn(AlunoRestController.class).get(aluno.getId())).withSelfRel());
	}
	
	@Override
	protected AlunoResource instantiateResource(Aluno aluno) {
		return new AlunoResource(aluno);
	}

}