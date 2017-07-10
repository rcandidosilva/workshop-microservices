# Laboratório 10

## Objetivos
- Trabalhando com JSON Web Tokens

## Tarefas

### Adicione o suporte JWT no serviço de segurança
- Utilize os projetos definidos no exercício anterior
- Adicione a dependência do `spring-security-jwt` no projeto do `security-server`
```xml
  <dependency>
      <groupId>org.springframework.security</groupId>
      <artifactId>spring-security-jwt</artifactId>
  </dependency>
```
- Configure o suporte ao JWT no projeto definindo por uma classe `JwtConfig`
```java
  @Configuration
  public class JwtConfig {

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

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
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

### Adicione informações adicionais no JWT payload
- Utilize os projetos definidos anteriormente
- ...
- Execute e teste a aplicação

### Manipule chaves assimétricas com JWT
- Utilize os projetos definidos anteriormente
- Gere a chave privada utilizando a ferramenta `keytool`
```
keytool -genkeypair -alias mytest
                    -keyalg RSA
                    -keypass mypass
                    -keystore mytest.jks
                    -storepass mypass
```
- Exporte a chave pública a partir da chave privada gerada anteriormente
```
keytool -list -rfc --keystore mytest.jks | openssl x509 -inform pem -pubkey
```
- Configure a chave privada no serviço de autorização OAuth2
```java
  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
      JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
      KeyStoreKeyFactory keyStoreKeyFactory =
        new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
      converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
      return converter;
  }
```
- Configure a chave pública no serviço de recursos OAuth2
```java
  @Bean
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
```
- Execute e teste a aplicação
