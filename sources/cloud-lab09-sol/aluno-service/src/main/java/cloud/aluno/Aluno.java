package cloud.aluno;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {

	@Id @GeneratedValue
	Long id;
	String nome;
	Integer matricula;
	String email;

}