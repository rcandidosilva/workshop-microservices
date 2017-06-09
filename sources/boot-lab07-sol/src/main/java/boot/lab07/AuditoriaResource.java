package boot.lab07;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class AuditoriaResource extends Resource<Auditoria> {
	
	public AuditoriaResource(Auditoria auditoria, Link... links) {
		super(auditoria, links);
	}
	
}