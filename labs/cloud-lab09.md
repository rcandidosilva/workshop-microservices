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
- Configure o servidor de autorização OAuth2 utilizando a anotação `@EnableAuthorizationServer`
```java
  @Configuration
  @EnableAuthorizationServer
  public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
      @Autowired
      private AuthenticationManager authenticationManager;

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

      @Autowired
      private AuthenticationManager authenticationManager;

      @Override
      protected void configure(HttpSecurity http) throws Exception {
          http.requestMatchers().antMatchers("/login", "/oauth/authorize")
            .and().authorizeRequests()
              .anyRequest().authenticated().and()
              .formLogin().permitAll();
      }

      @Override
      protected void configure(AuthenticationManagerBuilder auth) throws Exception {
          auth.inMemoryAuthentication()
            .withUser("barry").password("t0ps3cr3t").roles("USER");
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

### Teste o fluxo Client Credentials via protocolo OAuth2
- Utilize os projetos definidos anteriormente
- Modifique a configuração do `AuthServerConfig` no `security-server` para adicionar suporte ao fluxo de `Client Credentials`
```java
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      //...
      clients.inMemory().withClient("client-credentials")
           .secret("secret")
           .authorizedGrantTypes("client_credentials")
           .scopes("oauth2")
           .autoApprove(true) ;
  }
```
- Execute e teste a aplicação

### Teste o fluxo Authorization Code via protocolo OAuth2
- Utilize os projetos definidos anteriormente
- Modifique a configuração do `AuthServerConfig` no `security-server` para adicionar suporte ao fluxo de `Authorization Code`
```java
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      //...
      clients.inMemory().withClient("client-auth-code")
           .secret("secret")
           .authorizedGrantTypes("authorization_code")
           .scopes("oauth2")
           .autoApprove(true) ;
  }
```
- Execute e teste a aplicação

### Teste o fluxo Implicit via protocolo OAuth2
- Utilize os projetos definidos anteriormente
- Modifique a configuração do `AuthServerConfig` no `security-server` para adicionar suporte ao fluxo de `Implicit`
```java
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      //...
      clients.inMemory().withClient("client-implicit")
           .secret("secret")
           .authorizedGrantTypes("implicit")
           .scopes("oauth2")
           .autoApprove(true) ;
  }
```
- Execute e teste a aplicação
