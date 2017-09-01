# Laboratório 06

## Objetivos
- Implementando e manipulando segurança com o protocolo OAuth2 e JWT

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
      defaultZone: ${EUREKA_URI:http://localhost:8761/eureka}
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

### Teste o fluxo de geração tokens via protocolo OAuth2
- Utilize os projetos definidos anteriormente
- Modifique a configuração do `AuthServerConfig` no `security-server` para adicionar suporte ao fluxo de resource owner password
```java
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      clients.inMemory().withClient("client")
           .secret("secret")
           .authorizedGrantTypes("password", "client_credentials", "authorization_code", "implicit")
           .scopes("oauth2")
           .autoApprove(true) ;
  }
```
- Execute e teste a aplicação
  - Execute a seguinte requisição HTTP POST
    - `http://client:secret@localhost:9999/oauth/token?grant_type=password&username=barry&password=t0ps3cr3t`
    - Verifique como resultado o OAuth2 `access_token` retornado via username / password
  - Execute a seguinte requisição HTTP POST
    - `http://client:secret@localhost:9999/oauth/token?grant_type=client_credentials`
    - Verifique como resultado o OAuth2 `access_token` retornado via client credentials

### Adicione o suporte JWT no serviço de segurança
- Utilize os projetos definidos no exercício anterior
- Adicione a dependência do `spring-security-jwt` no projeto do `security-server`
```xml
  <dependency>
      <groupId>org.springframework.security</groupId>
      <artifactId>spring-security-jwt</artifactId>
  </dependency>
```
- Configure o suporte ao JWT ao serviço de autorização OAuth2 no projeto definindo por uma classe `AuthServerJwtConfig`
```java
  @Configuration
  public class AuthServerJwtConfig {
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        return converter;
    }

  }
```
- Modifique a configuração do servidor de autorização OAuth2 para adicionar suporte ao JWT
```java
  @Configuration
  @EnableAuthorizationServer
  public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
      //...
      @Autowired TokenStore tokenStore;      
      @Autowired JwtAccessTokenConverter accessTokenConverter;

      @Override
      public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
          endpoints.tokenStore(tokenStore)
                   .accessTokenConverter(accessTokenConverter)
                   .authenticationManager(authenticationManager);
      }
  }
```
- Configure o suporte ao JWT ao serviço de recursos OAuth2 no projeto definindo por uma classe `ResourceServerJwtConfig`
```java
  @Configuration
  public class ResourceServerJwtConfig {
    @Autowired TokenStore tokenStore;

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
     }    
  }
```
- Modifique a configuração do servidor de recursos OAuth2 para adicionar suporte ao JWT
```java
  @Configuration
  @EnableResourceServer
  public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
      //...
      @Autowired DefaultTokenServices tokenServices;

      @Override
      public void configure(ResourceServerSecurityConfigurer config) {
          config.tokenServices(tokenServices);
      }
  }
```
- Execute e teste a aplicação
  - Teste novamente os fluxos de autorização OAuth2 e verifique o JWT sendo utilizado

### [OPCIONAL]: Manipule chaves assimétricas com JWT
- Utilize os projetos definidos anteriormente
- Gere a chave privada utilizando a ferramenta `keytool`
```
  keytool -genkeypair -alias security-server
                      -keyalg RSA
                      -keypass mypass
                      -keystore mykeys.jks
                      -storepass mypass
```
- Será necessário instalar a ferramenta `openssl` para exportar a chave privada
  - Windows
    - http://gnuwin32.sourceforge.net/packages/openssl.htm
  - Mac OS
    - `brew install openssl`
  - Linux
    - https://geeksww.com/tutorials/libraries/openssl/installation/installing_openssl_on_ubuntu_linux.php
- Exporte a chave pública a partir da chave privada gerada anteriormente
```
  keytool -list -rfc --keystore mykeys.jks | openssl x509 -inform pem -pubkey
```
- Crie um arquivo `public.txt` com o conteúdo da chave pública retornada
```
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgIK2Wt4x2EtDl41C7vfp
OsMquZMyOyteO2RsVeMLF/hXIeYvicKr0SQzVkodHEBCMiGXQDz5prijTq3RHPy2
/5WJBCYq7yHgTLvspMy6sivXN7NdYE7I5pXo/KHk4nz+Fa6P3L8+L90E/3qwf6j3
DKWnAgJFRY8AbSYXt1d5ELiIG1/gEqzC0fZmNhhfrBtxwWXrlpUDT0Kfvf0QVmPR
xxCLXT+tEe1seWGEqeOLL5vXRLqmzZcBe1RZ9kQQm43+a9Qn5icSRnDfTAesQ3Cr
lAWJKl2kcWU1HwJqw+dZRSZ1X4kEXNMyzPdPBbGmU6MHdhpywI7SKZT7mX4BDnUK
eQIDAQAB
-----END PUBLIC KEY-----
```
- Adicione as chaves privada e pública geradas no diretório `src/main/resources` do projeto `security-server`
  - `mykeys.jks`
  - `public.txt`
- Configure a chave privada no suporte JWT do serviço de autorização OAuth2 definindo pela classe `AuthServerJwtConfig`
```java
  @Configuration
  public class AuthServerJwtConfig {
      //...   
      @Bean
      public JwtAccessTokenConverter accessTokenConverter() {
          JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
          KeyStoreKeyFactory keyStoreKeyFactory =
              new KeyStoreKeyFactory(new ClassPathResource("mykeys.jks"), "mypass".toCharArray());
          converter.setKeyPair(keyStoreKeyFactory.getKeyPair("security-server"));
          return converter;
      }
  }
```
- Configure a chave pública no suporte JWT do serviço de recursos OAuth2 definindo pela classe `ResourceServerJwtConfig`
```java
  @Configuration
  public class ResourceServerJwtConfig {
      //...         
      public JwtAccessTokenConverter accessTokenConverter() {
          JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
          Resource resource = new ClassPathResource("public.txt");
          String publicKey = null;
          try {
              publicKey = IOUtils.toString(resource.getInputStream());
          } catch (final IOException e) {
              throw new RuntimeException(e);
          }
          converter.setVerifierKey(publicKey);
          return converter;
      }
  }
```
- Execute e teste a aplicação
  - Teste novamente os fluxos de autorização OAuth2 e verifique o JWT via chaves assimétricas sendo validado
