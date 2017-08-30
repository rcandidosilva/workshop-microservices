# Laboratório 04

## Objetivos
- Implementando clientes REST com Netflix Feign

## Tarefas

### Implemente clientes REST com Feign
- Utilize os projetos definidos no exercício anterior
- Adicione a dependência `spring-cloud-starter-feign` nos projetos do `aluno-service` e `disciplina-service`
```xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-feign</artifactId>
  </dependency>
```
- Ative a configuração do Feign utilizando a anotação `@EnableFeignClients` nas aplicações
```java
  @EnableFeignClients
  @SpringBootApplication
  public class Application {
      public static void main(String[] args) {
          SpringApplication.run(Application.class, args);
      }
  }
```
- Implemente uma Feign interface para recuperar as disciplinas no projeto do `aluno-service`
```java
  @FeignClient("disciplina-service")
  public interface DisciplinaClient {

      @RequestMapping(value = "/disciplinas", method = RequestMethod.GET)
      Resources<DisciplinaDTO> getAllDisciplinas();

      @RequestMapping(value = "/disciplinas/{id}/dto", method = RequestMethod.GET)
      DisciplinaDTO getDisciplina(@PathVariable("id") Long id);
  }
```
- Refatore o REST endpoint para retornar o `AlunoDTO` com as disciplinas utilizando o client Feign definido
- Implemente uma interface Feign para recuperar os alunos no projeto `disciplina-service`
```java
  @FeignClient("aluno-service")
  public interface AlunoClient {

      @RequestMapping(value = "/alunos", method = RequestMethod.GET)
      Resources<AlunoDTO> getAllAlunos();

      @RequestMapping(value = "/alunos/{id}/dto", method = RequestMethod.GET)
      AlunoDTO getAluno(@PathVariable("id") Long id);
  }
```
- Refatore o REST endpoint para retornar a `DisciplinaDTO` com os alunos utilizando o client Feign definido
- Execute e teste a aplicação

### Customize as configurações do Feign na aplicação
- Utilize os projetos definidos anteriormente
- Defina uma classe de customização de configurações Feign `FeignConfiguration`
```java
  @Configuration
  public class FeignConfiguration {  
      // ...
  }
```
- Configure um nível de log `FULL` como padrão para os Feign clients
```java
  @Bean
  Logger.Level feignLoggerLevel() {
      return Logger.Level.FULL;
  }
```
- Habilite também um nível de log específico somente para o client Feign `DisciplinaClient`
```
logging:
  level:
    cloud.aluno.DisciplinaClient: DEBUG
```
- Configure um novo comportamento para timeout de conexões das requisições
```java
  int FIVE_SECONDS = 5000; // milliseconds
  @Bean
  public Request.Options options() {
      return new Request.Options(FIVE_SECONDS, FIVE_SECONDS);
  }
```
- Habilite também a compressão da requisição e resposta à ser realizada
```
feign:
  compression:
    request:
      enabled: true
    response:
      enabled: true  
```
- Execute e teste a aplicação
