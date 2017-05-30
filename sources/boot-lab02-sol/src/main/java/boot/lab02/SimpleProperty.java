package boot.lab02;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SimpleProperty {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${app.config.property.string}")
	private String stringValue;
	
	@Value("${app.config.property.long}")
	private Long longValue;
	
	@Value("${app.config.property.boolean}")
	private Boolean booleanValue;
	
	@Value("${app.config.property.random}")
	private Long randomValue;
	
	@Autowired
	private Environment env;
	
	@PostConstruct
	public void init() {
		logger.debug("app.config.property.string = {}", stringValue);
		logger.debug("app.config.property.long = {}", longValue);
		logger.debug("app.config.property.boolean = {}", booleanValue);
		logger.debug("app.config.property.random = {}", randomValue);
		logger.debug("app.config.property.env = {}", env.getProperty("app.config.property.env"));
	}
	
}
