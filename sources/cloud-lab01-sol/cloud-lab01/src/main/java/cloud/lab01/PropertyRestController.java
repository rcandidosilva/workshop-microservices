package cloud.lab01;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class PropertyRestController {
	
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
	
	@RequestMapping("/properties/print")
	public Map<String, Object> printProperties() {
		Map<String, Object> props = new HashMap<>();
		props.put("app.config.property.string", stringValue);
		props.put("app.config.property.long", longValue);
		props.put("app.config.property.boolean", booleanValue);
		props.put("app.config.property.random", randomValue);
		props.put("app.config.property.env", env.getProperty("app.config.property.env"));
		return props;
	}
	
}