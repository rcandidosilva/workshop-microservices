# Laboratório 12

## Objetivos
- Aplicando segurança nos serviços da plataforma Spring Cloud

## Tarefas

### Protega o Config Server
- Utilize os projetos definidos no exercício anterior
- Adicione a dependência do `spring-boot-starter-security` no projeto do `config-server`
```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
  </dependency>
```
- Configure as credentials para acesso ao Config Server no arquivo `application.yml` do projeto `config-server`
```
security:
  user:
    name: configUser
    password: configPassword
    role: SYSTEM
```
- Execute e teste a aplicação
  - Foi possível recuperar as configurações via Config Server pelos demais serviços durante o startup?
- Modifique a configuração para acesso ao Config Server no arquivo `bootstrap.yml` dos demais projetos existentes
```
spring:
  cloud:
    config:
      uri: http://localhost:8888
      username: configUser
      password: configPassword      
```
- Execute e teste novamente a aplicação
  - Deverá ser possível acessar corretamente as configurações via Config Server

### Protega o Eureka Server
- Utilize os projetos definidos anteriormente
- Adicione a dependência do `spring-boot-starter-security` no projeto do `eureka-server`
```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
  </dependency>
```
- Defina uma classe `SecurityConfig` no projeto do `eureka-server`
```java
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
```
- Execute e teste a aplicação
  - Foi possível registrar os demais serviços da aplicação no Eureka Server?
  - Tente acessar o Eureka Dashboard. Será necessário realizar um login para acesso (eurekaUser/eurekaPassword)
    - http://localhost:8761/
- Modifique a configuração de conexão ao Eureka Server nas propriedades dos demais serviços da aplicação
```
eureka:
 client:
   serviceUrl:
     defaultZone: ${EUREKA_URI:http://eurekaUser:eurekaPassword@localhost:8761/eureka}
 instance:
   preferIpAddress: true
```
- Execute e teste novamente a aplicação
  - Deverá ser possível registrar todos os serviços no Eureka Server

### Protega o Hystrix Dashboard
- Utilize os projetos definidos anteriormente
- Adicione a dependência do `spring-boot-starter-security` no projeto do `hystrix-dashboard`
```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
  </dependency>
```
- Configure a restrição para acesso nas propriedades do projeto `hystrix-dashboard`
```
security:
  basic:
    enabled: true
  user:
    name: hystrixUser
    password: hystrixPassword
    role: SYSTEM
```
- Execute e teste a aplicação
  - Será necessário realizar um login de acesso para o Hystrix Dashboard (hystrixUser / hystrixPassword)
    - http://localhost:7979/hystrix
  - Para acessar o stream dos circuitos monitorados será necessário também informar o usuário e password na URL
    - http://hystrixUser:hystrixPassword@localhost:7979/turbine.stream
