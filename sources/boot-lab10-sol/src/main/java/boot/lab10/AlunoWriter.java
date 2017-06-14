package boot.lab10;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlunoWriter implements ItemWriter<Aluno> {
	
	@Autowired
	AlunoRepository repository;
	
	@Override
	public void write(List<? extends Aluno> alunos) throws Exception {
		repository.save(alunos);		
	}

}