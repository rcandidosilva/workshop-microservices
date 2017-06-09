package boot.lab07;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class AuditoriaResourceAssembler extends ResourceAssemblerSupport<Auditoria, AuditoriaResource> {
	
	public AuditoriaResourceAssembler() {
		super(Auditoria.class, AuditoriaResource.class);
	}

	@Override
	public AuditoriaResource toResource(Auditoria auditoria) {
		return new AuditoriaResource(auditoria, linkTo(methodOn(AuditoriaRestController.class).get(auditoria.getUuid())).withSelfRel());
	}
	
	@Override
	protected AuditoriaResource instantiateResource(Auditoria auditoria) {
		return new AuditoriaResource(auditoria);
	}

}