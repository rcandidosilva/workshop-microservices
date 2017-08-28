package boot.lab06;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("barry").password("t0ps3cr3t").roles("USER")
			.and().withUser("larry").password("t0ps3cr3t").roles("USER", "MANAGER")
			.and().withUser("root").password("t0ps3cr3t").roles("USER", "MANAGER", "ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/swagger-ui.html", "/swagger-resources/**").permitAll()
			.antMatchers("/alunos/**").hasAnyRole("USER")
			.antMatchers("/disciplinas/**").hasAnyRole("MANAGER")
			.anyRequest().fullyAuthenticated()
			.and().httpBasic().and().csrf().disable();
	}
}