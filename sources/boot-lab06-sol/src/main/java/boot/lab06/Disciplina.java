package boot.lab06;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Disciplina {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	String nome;
	Integer cargaHoraria;
	Date dataInicio;
	
}
