package boot.lab10;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig {

	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job processJob(Step jobStep) {
		return jobBuilderFactory.get("processJob")
				.incrementer(new RunIdIncrementer())
				.flow(jobStep).end().build();
	}

	@Bean
	public Step jobStep(AlunoReader alunoReader, 
			AlunoProcessor alunoProcessor, AlunoWriter alunoWriter) {
		return stepBuilderFactory.get("orderStep1").<String, Aluno> chunk(10)
				.reader(alunoReader).processor(alunoProcessor)
				.writer(alunoWriter).build();
	}

}