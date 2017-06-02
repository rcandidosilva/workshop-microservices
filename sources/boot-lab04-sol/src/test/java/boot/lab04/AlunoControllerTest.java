package boot.lab04;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(AlunoController.class)
public class AlunoControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
    @Test
    public void testListAlunos() throws Exception {
        mockMvc.perform(get("/"))
			.andExpect(model().hasNoErrors())
			.andExpect(model().attributeExists("alunos"))
			.andExpect(view().name("index"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Lista de Alunos")));
    }
	
    @Test
    public void testAddAluno() throws Exception {
        mockMvc.perform(get("/add"))
			.andExpect(model().hasNoErrors())
			.andExpect(model().attributeExists("aluno"))
			.andExpect(view().name("aluno"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Novo Aluno")));
    }
    
    @Test
    public void testSaveAluno() throws Exception {
        mockMvc.perform(post("/save")
        		.param("id", "1")
        		.param("nome", "Rodrigo")
        		.param("matricula", "1111")
        		.param("email", "rodrigo@email.com"))
			.andExpect(status().is3xxRedirection());
    }
    
    @Test
    public void testDeleteAluno() throws Exception {
        mockMvc.perform(get("/delete/{id}", 1))
        	.andExpect(status().is3xxRedirection());
    }
}
