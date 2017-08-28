# Laboratório 6

## Objetivos
- Habilitando segurança com Spring Boot

## Tarefas
### Habilite segurança no projeto Spring Boot
- Utilize o projeto criado no exercício anterior
- Configure a dependência do Spring Security
```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
  </dependency>
```
- Configure as informações de usuário e senha no `application.properties`
```
security.user.name=root
security.user.password=t0ps3cr3t
```
- Execute e teste a aplicação

### Configure diferentes usuários e roles para acessar a aplicação
- Utilize o projeto definido anteriormente
- Defina uma nova configuração de segurança para habilitar uma lista de usuários disponíveis para autenticação
  - Utilize como modelo de configuração o exemplo abaixo:
```java
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                .withUser("barry").password("t0ps3cr3t").roles("USER");
    }
}
```
- Execute e teste a aplicação acessando-a com diferentes usuários

### Utilize diferentes privilégios na autorização dos endpoints da aplicação
- Utilize o projeto defindo anteriormente
- Adicione algumas restrições de segurança via configuração centralizada
  - Utilize como modelo de configuração o exemplo abaixo:
```java
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/foo/**").hasAnyRole("USER")
			.antMatchers("/bar/**").hasAnyRole("MANAGER")
			.anyRequest().fullyAuthenticated()
			.and().httpBasic().and().csrf().disable();
	}
}
```
- Habilite a configuração para uso de anotações de segurança nos métodos da aplicação
```java
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
```
- Defina algumas restrições de segurança utilizando as anotações `@Secured` e `@PreAuthorize`
- Execute e teste a aplicação
