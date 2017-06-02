package boot.lab04;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.SessionScope;

@Controller
@SessionScope
public class AlunoController {
	
	private List<Aluno> list = new ArrayList<>();
	
	@PostConstruct
	public void init() {
		list.add(new Aluno(1l, "John", 11111, "john@john.com"));
		list.add(new Aluno(2l, "Steve", 22222, "steve.stevent@st.com"));
		list.add(new Aluno(3l, "Mary", 33333, "mary@robinson.com"));
		list.add(new Aluno(4l, "Kate", 44444,"kate@kate.com"));
		list.add(new Aluno(5l, "Diana", 55555,"diana@doll.com"));
	}

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("alunos", list);
		return "index";
	}

	@RequestMapping(value = "/add")
	public String add(Model model) {
		model.addAttribute("aluno", new Aluno());
		return "aluno";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Aluno aluno) {
		list.add(aluno);
		return "redirect:/";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, Model model) {
		list.removeIf(s -> s.getId() == id);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/throw/error")
	public String throwIOException() throws IOException {
		throw new IOException();
	}
	
	@ExceptionHandler(IOException.class)
	@ResponseBody
	public ResponseEntity<String> handleIOException(IOException ex) {
		return ResponseEntity.ok("Custom handler for IOException...");
	}
	
}