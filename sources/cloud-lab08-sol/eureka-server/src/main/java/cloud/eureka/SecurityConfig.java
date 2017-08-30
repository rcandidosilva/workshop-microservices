package cloud.eureka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("eurekaUser").password("eurekaPassword").roles("SYSTEM");
	}	
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().csrf().disable()
			.authorizeRequests().anyRequest().authenticated().and()
			.authorizeRequests().anyRequest().hasRole("SYSTEM");
	}
	
}