package boot.lab10;

import java.text.SimpleDateFormat;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class AlunoProcessor implements ItemProcessor<String, Aluno> {

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public Aluno process(String line) throws Exception {
		String[] data = line.split(",");
		return Aluno.builder()
			.nome(data[0])
			.matricula(new Integer(data[1]))
			.email(data[2])
			.dataNascimento(dateFormat.parse(data[3]))
			.build();
	}

}
