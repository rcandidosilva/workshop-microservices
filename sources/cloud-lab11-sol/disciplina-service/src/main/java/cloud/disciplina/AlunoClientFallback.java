package cloud.disciplina;

import java.util.ArrayList;

import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

@Component
public class AlunoClientFallback implements AlunoClient {

	@Override
	public AlunoDTO getAluno(Long id) {
		return new AlunoDTO();
	}
	
	@Override
	public Resources<AlunoDTO> getAllAlunos() {				
		return new Resources<>(new ArrayList<>());
	}

}