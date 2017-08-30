package cloud.disciplina;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlunoDTO {
	
	Long id;
	String nome;
	String email;
	Integer matricula;
	List<String> disciplinas;
	
}
