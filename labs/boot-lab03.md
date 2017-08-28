# Laboratório 3

## Objetivos
- Implementando RESTful com Spring Boot

## Tarefas
### Implemente uma API RESTful API para controlar alunos
- Crie um novo projeto Spring Boot
- Implemente um classe para representar o objeto `Aluno`
```java
class Aluno {
  Long id;
  String nome;
  Integer matricula;
  String email;
  // getters/setters
}
```
- Implemente um `@RestController` com os endpoints RESTful para gerenciar as ações de `listar`, `criar`, `atualizar` e `excluir`
- Execute e teste a aplicação
  - DICA: Utilize um cliente REST para poder simular as chamadas aos endpoints RESTful (Postman: https://www.getpostman.com)

### Manipule a negociação de conteúdo com REST
- Utilize o projeto definido anteriormente
- Realize a configuração para negociação de conteúdo JSON e/ou XML (conforme slide)
- Revise a implementação dos endpoints RESTful para configurar o suporte à serialização de retorno JSON e XML
```java
@RequestMapping(path = "/service",
                method = RequestMethod.GET,
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
```
- Adicione o suporte a renderização (parser) XML no bean de `Aluno`
```java
@XmlRootElement
public class Aluno {
  ...
}
```
- Execute e teste a aplicação negociando o conteúdo desejado

### Configure o comportamento HATEOAS na API REST
- Utilize o projeto definido anteriormente
- Configure as dependências do Spring HATEOAS
```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-hateoas</artifactId>
  </dependency>
```
- Modifique a implementação do objeto `Aluno` para tornar-se um `ResourceSupport`
- Modifique a implementação dos endpoints RESTful para adicionar os hyperlinks HATEOAS
- Execute e teste a aplicação

### Implemente uma classe de teste para @RestController
- Utilize o projeto defindo anteriormente
- Implemente uma classe de teste para a API RESTful de alunos
- Utilize a configuração `@WebMvcTest` para facilitar a implementação do unit test
- Injete o objeto MockMvc para simular as requisições HTTP send realizadas no controller
- Execute a classe de teste com sucesso

### Documente API com Swagger
- Utilize o projeto definido anteriormente
- Configure as dependências do Swagger Code e UI
```xml
  <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger2</artifactId>
      <version>2.6.1</version>
  </dependency>

  <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger-ui</artifactId>
      <version>2.6.1</version>
  </dependency>
```
- Habilite a configuração do Swagger na aplicação Spring Boot
```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build();                                           
    }
}
```
- Documente algums endpoints RESTful que foram implementados utilizando as Swagger annotations
  - `@ApiOperation`, `@ApiResponse`, `@ApiParam`, etc
- Execute a aplicação e verifique a documentação publicada
