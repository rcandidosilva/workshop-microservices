package boot.lab10;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;

@EnableCaching
@EnableBatchProcessing 
@SpringBootApplication
@Import(SpringDataRestConfiguration.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
