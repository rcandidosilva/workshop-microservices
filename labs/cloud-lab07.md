# Laboratório 07

## Objetivos
- Protegendo os microservices com Spring Cloud Security

## Tarefas

### Protega o microservice de Aluno
- Utilize os projetos definidos no exercício anterior
- Adicione as dependência do `spring-cloud-starter-security`, `spring-security-oauth2`, `spring-security-jwt` no projeto `aluno-service`
```xml
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-security</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.security.oauth</groupId>
    <artifactId>spring-security-oauth2</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-jwt</artifactId>
  </dependency>
```
- Configure o serviço de recursos OAuth2 utilizando a anotação `@EnableResourceServer` no projeto `aluno-service`
```java
  @Configuration
  @EnableResourceServer
  public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
  	public void configure(HttpSecurity http) throws Exception {
  		  http.csrf().disable().authorizeRequests()
  			   .anyRequest().authenticated();
  	}
  }
```
- Configure as seguintes propriedades do `aluno-service` gerenciadas pelo Config Server
```
security:
  sessions: stateless
  basic:
    enabled: false
  user:
    password: none    
  oauth2:
    resource:
      jwt:
        keyValue: |
            -----BEGIN PUBLIC KEY-----
            MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo1jWPfjvJxaXCHzvClU7
            uJg+6AlZ8ht1Rbr+7Wo5o+YBWgCc6lZmSv/mwxvfL/wqagQ/W756a8vUJ7qFz/k9
            eBSJQSRuzJ6pT4OMMR9gbmYroh3RM/Xd5RelJgT3+OrvjAZr1pFYdAwp0q1T9XPa
            6PnCXq8KhIqNPxMjcaBrOycWEgWE4g4VnnrKDLtMmEZZIc0EMv8j7womsyNkbTyl
            nPsbFttNwtFoTVJeqvD01Fd6ISaoOVQAUfAcxvp77B/A1g0No3GHBupEtW3Hgp2/
            80Zl0+Gwjl6Wag5Mu9H7MIUPo+4xFGAJ0uwseHiErZqdWlHIo179IacB87+9Vt0g
            pwIDAQAB
            -----END PUBLIC KEY-----
```
- Observe na configuração dos recursos OAuth2 esta configurada para modo `Stateless` e utilizando a validação JWT no cliente
- Execute e teste a aplicação
  - Tente acessar os REST endpoints do `aluno-service` sem informar nenhum token na requisição
  - Recupere um OAuth2 token via `security-server` e tente acessar os REST endpoints com esse token adicionado no header da requisição
    - `Authorization: Bearer [token]`
  - Identifique também que a validação deste token acontece de maneira `Stateless` sem precisar acessar o `security-server`

### Protega o microservice de Disciplina
- Utilize os projetos definidos no exercício anterior
- Adicione as dependência do `spring-cloud-starter-security`, `spring-security-oauth2`, `spring-security-jwt` no projeto `disciplina-service`
```xml
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-security</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.security.oauth</groupId>
    <artifactId>spring-security-oauth2</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-jwt</artifactId>
  </dependency>
```
- Configure o serviço de recursos OAuth2 utilizando a anotação `@EnableResourceServer` no projeto `disciplina-service`
```java
  @Configuration
  @EnableResourceServer
  public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
  	public void configure(HttpSecurity http) throws Exception {
  		  http.csrf().disable().authorizeRequests()
  			   .anyRequest().authenticated();
  	}
  }
```
- Configure as seguintes propriedades do `disciplina-service` gerenciadas pelo Config Server
```
security:
  basic:
    enabled: false
  user:
    password: none    
  oauth2:
    resource:
      preferTokenInfo: false
      userInfoUri: http://localhost:9999/users/current
```
- Observe na configuração dos recursos OAuth2 esta configurada para validação do JWT via `security-server`
- Execute e teste a aplicação
  - Tente acessar os REST endpoints do `disciplina-service` sem informar nenhum token na requisição
  - Recupere um OAuth2 token via `disciplina-service` e tente acessar os REST endpoints com esse token adicionado no header da requisição
    - `Authorization: Bearer [token]`
  - Identifique também que durante a validação do token é realizado uma chamada ao `security-server`


### [OPCIONAL]: Configure o suporte a utilização de restrições de segurança OAuth2 via anotações
- Utilize os projetos definidos anteriormente
- Defina uma classe `SecurityMethodConfig` para adicionar o suporte a restrições de segurança via anotações nos projetos `aluno-service` e `disciplina-service`
```java
  @Configuration
  @EnableGlobalMethodSecurity(prePostEnabled = true)
  public class SecurityMethodConfig extends GlobalMethodSecurityConfiguration {

	  @Override
	  protected MethodSecurityExpressionHandler createExpressionHandler() {
		  return new OAuth2MethodSecurityExpressionHandler();
	  }

  }
```
- Adicione a seguinte restrição de segurança para acessar o REST endpoint `/disciplinas/nomes` no `DisciplinaRestController`
```java
  @RestController
  @RequestMapping("/disciplinas")
  public class DisciplinaRestController {
    //...

	  @PreAuthorize("hasRole('MANAGER')")
	  @GetMapping("/nomes")
	  public List<String> getDisciplinas() {
		  return repository.findAll()
				  .stream().map(d -> d.getNome()).collect(Collectors.toList());
	  }
  }
```
- Adicione a seguinte restrição de segurança para acessar o REST endpoint `/alunos/nomes` no `AlunoRestController`
```java
  @RestController
  @RequestMapping("/alunos")
  public class AlunoRestController {
    //...

	  @PreAuthorize("#oauth2.isUser()")
	  @GetMapping("/nomes")
	  public List<String> getAlunos() {
		  return repository.findAll()
				  .stream().map(a -> a.getNome()).collect(Collectors.toList());
	  }
  }
```
- Execute e teste a aplicação
  - Tente acessar o REST endpoint `/disciplinas/nomes` com um token para um usuário sem o perfil `MANAGER`
  - Tente acessar o REST endpoint `/alunos/nomes` com um token gerado via fluxo OAuth2 `client_credentials`

### [OPCIONAL]: Configure o repasse do contexto de segurança OAuth2 nas requisições via Feign
- Utilize os projetos definidos anteriormente
- Execute e teste a aplicação tentando acessar os REST endpoints que realizam chamadas via Feign para outros serviços. Verifique se os dados solicitados foram todos retornados.
  - REST `/disciplinas/{id}/dto`
  - REST `/alunos/{id}/dto`
- Adicione a seguinte configuração do `ResourceServerConfig` nos projetos do `aluno-service` e `disciplina-service`
```java
  @Configuration
  @EnableResourceServer
  public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	  //...

    @Bean
    public OAuth2FeignRequestInterceptor feignRequestInterceptor(
            OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails resource) {
        return new OAuth2FeignRequestInterceptor(oAuth2ClientContext, resource);
    }
  }
```
- Execute e teste a aplicação novamente
  - Foi possível acessar os dados dos alunos via REST endpoint `/disciplinas/{id}/dto`?
  - Foi possível acessar os dados das disciplinas via REST endpoint `/alunos/{id}/dto`?
- Modifique a configuração do circuit breaker Hystrix na classe `DisciplinaServiceProxy` para utilizar a estratégia `SEMAPHORE`
```java
  @Service
  public class DisciplinaServiceProxy {
    //...

	  @HystrixCommand(fallbackMethod = "getNomesDisciplinasFallback",
			commandProperties = {
					@HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"),
					@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),
					@HystrixProperty(name="requestCache.enabled", value="false")
			})
	  List<String> getNomesDisciplinas() {
		  Resources<DisciplinaDTO> disciplinas = disciplinaClient.getAllDisciplinas();
		  return disciplinas.getContent().stream()
				.map(d -> d.getNome()).collect(Collectors.toList());
	  }
  }
```
- Execute e teste novamente o acesso ao REST endpoint `/alunos/{id}/dto`. Foi possível acessar os dados de disciplina agora?
