package boot.lab03;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Aluno implements Serializable {

	Long id;
	String nome;
	Integer matricula;
	String email;
	
	public Aluno() {
		super();
	}
	
	public Aluno(Long id, String nome, Integer matricula, String email) {
		super();
		this.id = id;
		this.nome = nome;
		this.matricula = matricula;
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getMatricula() {
		return matricula;
	}
	
	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
}