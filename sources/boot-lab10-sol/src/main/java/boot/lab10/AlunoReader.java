package boot.lab10;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.annotation.PostConstruct;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
public class AlunoReader implements ItemReader<String> {

	Queue<String> lines = new PriorityQueue<>();
	
	@PostConstruct
	public void init() throws Exception {
		try (InputStream is = ClassLoader.getSystemResourceAsStream("alunos_import.csv"); 
				BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			reader.readLine(); // first line is the header
			String line = null;
			do {
				line = reader.readLine();
				if (line != null)
					lines.offer(line);
			} while (line != null);
		}
	}
	
	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		return lines.poll();
	}	

}