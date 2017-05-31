package boot.lab03;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JsonTest
public class AlunoTest {

	@Autowired
	private JacksonTester<Aluno> json;
	
	@Test
	public void testSerializacao() throws Exception {
		Aluno aluno = new Aluno(1l, "João Silva", 1111, "joao@email.com");
        assertThat(this.json.write(aluno)).hasJsonPathStringValue("@.nome");
        assertThat(this.json.write(aluno)).extractingJsonPathStringValue("@.nome")
                .isEqualTo("João Silva");
	}
	
    @Test
    public void testDeserializacao() throws Exception {
        String content = "{\"id\":1, \"nome\":\"João Silva\",\"matricula\":1111, \"email\":\"joao@email.com\"}";
        assertThat(this.json.parse(content))
                .isEqualTo(new Aluno(1l, "João Silva", 1111, "joao@email.com"));
        assertThat(this.json.parseObject(content).getNome()).isEqualTo("João Silva");
    }
}
