# Laboratório 09

## Objetivos
- Implementando e manipulando segurança com o protocolo OAuth2

## Tarefas

### Implemente um serviço de segurança utilizando o protocolo OAuth2
- Utilize os projetos definidos no exercício anterior
- Crie um novo projeto Spring Boot para representar o serviço de segurança `security-server`
- Configure o suporte da plataforma Spring Cloud no `pom.xml`
```xml
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Dalston.SR1</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```
- Adicione as seguintes dependências no projeto
  - `spring-boot-starter-web`
  - `spring-boot-starter-actuator`
  - `spring-cloud-starter-config`
  - `spring-cloud-starter-eureka`
- Adicione também a dependência `spring-security-oauth2` no seu projeto
```xml
  <dependency>
      <groupId>org.springframework.security.oauth</groupId>
      <artifactId>spring-security-oauth2</artifactId>
  </dependency>
```
- Configure o serviço de autorização OAuth2 utilizando a anotação `@EnableAuthorizationServer`
```java
  @Configuration
  @EnableAuthorizationServer
  public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
      @Autowired
      @Qualifier("authenticationManagerBean")
      AuthenticationManager authenticationManager;

      @Override
      public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
          oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
      }

      @Override
      public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
          // TODO define the client details
      }

      @Override
      public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
          endpoints.authenticationManager(authenticationManager);
      }
  }
```
- Configure os detalhes de segurança (usuário, perfil, etc) para serem utilizados pela aplicação
```java
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
```
- Configure o serviço de recursos OAuth2 utilizando a anotação `@EnableResourceServer`
```java
  @Configuration
  @EnableResourceServer
  public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	    @Override
	    public void configure(HttpSecurity http) throws Exception {
		      http.authorizeRequests()
			        .antMatchers("/users/ping").permitAll()
			        .antMatchers("/users/current").authenticated()
			        .anyRequest().authenticated();
	    }

  }
```
- Implemente também um REST controller para retornar as informações dos usuários
```java
  @RestController
  @RequestMapping("/users")
  public class UserRestController {

	    @RequestMapping("/current")
	    public Principal current(Principal principal) {
		      return principal;
	    }

	    @RequestMapping("/ping")
	    public ResponseEntity<String> ping() {
		      return ResponseEntity.ok("ping: " + System.currentTimeMillis());
	    }
  }
```
- Adicione a configuração do novo serviço de segurança `security-server` no Config Server
```
server:
  port: ${PORT:9999}

eureka:
  client:
    serviceUrl:
      defaultZone: ${EUREKA_URI:http://localhost:9999/eureka}
  instance:
    preferIpAddress: true

security:
  basic:
    enabled: false    
```
- Não se esqueça de configurar o `bootstrap.yml` na aplicação para se conectar com o Config Server
```
spring:
  application:
    name: security-server
  cloud:
    config:
      uri: http://localhost:8888  
```
- Adicione também a dependência do `spring-cloud-starter-config` no projeto
- Execute e teste a aplicação

### Teste o fluxo Resource Owner Password via protocolo OAuth2
- Utilize os projetos definidos anteriormente
- Modifique a configuração do `AuthServerConfig` no `security-server` para adicionar suporte ao fluxo de resource owner password
```java
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      clients.inMemory().withClient("client-password")
           .secret("secret")
           .authorizedGrantTypes("password")
           .scopes("oauth2")
           .autoApprove(true) ;
  }
```
- Execute e teste a aplicação
  - Execute a seguinte requisição HTTP POST
    - `http://client-password:secret@localhost:9999/oauth/token?grant_type=password&username=barry&password=t0ps3cr3t`
  - Verifique como resultado o OAuth2 `access_token` retornado

### Teste o fluxo Client Credentials via protocolo OAuth2
- Utilize os projetos definidos anteriormente
- Modifique a configuração do `AuthServerConfig` no `security-server` para adicionar suporte ao fluxo de `Client Credentials`
```java
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      //...
        .and().withClient("client-credentials")
           .secret("secret")
           .authorizedGrantTypes("client_credentials")
           .scopes("oauth2")
           .autoApprove(true) ;
  }
```
- Execute e teste a aplicação
  - Execute a seguinte requisição HTTP POST
    - `http://client-credentials:secret@localhost:9999/oauth/token?grant_type=client_credentials`
  - Verifique como resultado o OAuth2 `access_token` retornado

### Teste o fluxo Authorization Code via protocolo OAuth2
- Utilize os projetos definidos anteriormente
- Modifique a configuração do `AuthServerConfig` no `security-server` para adicionar suporte ao fluxo de `Authorization Code`
```java
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      //...
      .and().withClient("client-auth-code")
           .secret("secret")
           .authorizedGrantTypes("authorization_code")
           .scopes("oauth2")
           .autoApprove(true) ;
  }
```
- Execute e teste a aplicação
  - Abra um web browser e accesse a seguinte URL
    - `http://localhost:9999/oauth/authorize?response_type=code&client_id=client-auth-code&scope=oauth2&redirect_uri=http://callback`
  - Verifique a tela de login sendo retornada e digite uma credencial válida (user: barry / pass: t0ps3cr3t)
  - Observe a URL de callback sendo retornada com um código de autorização OAuth2 `code` retornado
    - Exemplo: `http://callback/?code=WVewpf`
  - Execute a seguinte requisição HTTP POST
    - `http://client-auth-code:secret@localhost:9999/oauth/token?grant_type=authorization_code&code=WVewpf&redirect_uri=http://callback`
  - Verifique como resultado o OAuth2 `access_token` retornado

### Teste o fluxo Implicit via protocolo OAuth2
- Utilize os projetos definidos anteriormente
- Modifique a configuração do `AuthServerConfig` no `security-server` para adicionar suporte ao fluxo de `Implicit`
```java
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      //...
      .and().withClient("client-implicit")
           .secret("secret")
           .authorizedGrantTypes("implicit")
           .scopes("oauth2")
           .autoApprove(true) ;
  }
```
- Execute e teste a aplicação
  - Abra um web browser e acesse a seguinte URL
    - `http://localhost:9999/oauth/authorize?response_type=token&client_id=client-implicit&redirect_uri=http://callback`
  - Verifique como resultado o OAuth2 `access_token` sendo retornado implicitamente na URL de callback
