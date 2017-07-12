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

### Adicione informações adicionais no JWT payload
- Utilize os projetos definidos anteriormente
- Defina uma classe `JwtTokenEnhancer` para incrementar informações adicionais ao JWT retornado no projeto `security-server`
```java
  public class JwtTokenEnhancer implements TokenEnhancer {
      @Override
      public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
          OAuth2Authentication authentication) {
          Map<String, Object> additionalInfo = new HashMap<>();
          additionalInfo.put("organization", authentication.getName() + System.currentTimeMillis());
          ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
          return accessToken;
      }
  }
```
- Adicione a configuração para incrementar informações adicionais JWT na classe de configuração `AuthServerJwtConfig`
```java
  @Configuration
  public class AuthServerJwtConfig {
      //...
      @Bean
      public TokenEnhancer tokenEnhancer() {
          TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
          tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new JwtTokenEnhancer(),
            accessTokenConverter()));
          return tokenEnhancerChain;
      }
  }
```
- Modifique a configuração do servidor de autorização OAuth2 para adicionar suporte as informações customizadas ao JWT
```java
  @Configuration
  @EnableAuthorizationServer
  public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
      //...
      @Autowired TokenEnhancer tokenEnhancer;

      @Override
      public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
          endpoints.tokenStore(tokenStore)
              .tokenEnhancer(tokenEnhancer)
              .authenticationManager(authenticationManager);
      }
  }
```
- Execute e teste a aplicação
  - Teste novamente os fluxos de autorização OAuth2 e verifique o JWT modificado sendo retornado

### Manipule chaves assimétricas com JWT
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
