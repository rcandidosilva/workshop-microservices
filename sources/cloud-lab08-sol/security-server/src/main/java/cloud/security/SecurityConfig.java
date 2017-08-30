package cloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("barry").password("t0ps3cr3t").roles("USER").and()
			.withUser("larry").password("t0ps3cr3t").roles("USER", "MANAGER").and()
			.withUser("root").password("t0ps3cr3t").roles("USER", "MANAGER", "ADMIN");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        	.requestMatchers().antMatchers("/login", "/oauth/authorize").and()
        		.authorizeRequests().anyRequest().authenticated().and()
        		.formLogin().permitAll();
	}		

}