# Laboratório 05

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
- Ative a configuração do Feign utilizando a anotação `@EnableFeignClients` na aplicação
```java
  @EnableFeignClients
  @SpringBootApplication
  public class Application {
      public static void main(String[] args) {
          SpringApplication.run(Application.class, args);
      }
  }
```
- Implemente uma Feign interface para recuperar as disciplinas no projeto `aluno-service`
```java
  @FeignClient("disciplina-service")
  public interface DisciplinaClient {

	    @RequestMapping(value = "/disciplinas", method = RequestMethod.GET)
	    Resources<DisciplinaDTO> getAllDisciplinas();

	    @RequestMapping(value = "/disciplinas/{id}", method = RequestMethod.GET)
	    DisciplinaDTO getDisciplina(@PathVariable("id") Long id);

  }
```
- Refatore o REST endpoint para retornar o `AlunoDTO` com as disciplinas utilizando o Feign client definido
- Implemente uma Feign interface para recuperar os alunos no projeto `disciplina-service`
```java
  @FeignClient("aluno-service")
  public interface AlunoClient {

	    @RequestMapping(value = "/alunos", method = RequestMethod.GET)
	    Resources<AlunoDTO> getAllAlunos();

	    @RequestMapping(value = "/alunos/{id}", method = RequestMethod.GET)
	    AlunoDTO getAluno(@PathVariable("id") Long id);

  }
```
- Refatore o REST endpoint para retornar a `DisciplinaDTO` com os alunos utilizando o Feign client definido
- Execute e teste a aplicação

### Customize as configurações do Feign na aplicação
- Utilize os projetos definidos anteriormente
- Defina uma classe de customização de configurações Feign `CustomFeignConfiguration`
```java
  @Configuration
  public class CustomFeignConfiguration {  
      // TODO configurations
  }
```
- Configure um nível de log `FULL` como padrão para os Feign clients
```java
  @Bean
  Logger.Level feignLoggerLevel() {
      return Logger.Level.FULL;
  }
```
- Habilite também um nível de log específico (DEBUG) para o Feign client `DisciplinaClient`
```
logging.level.cloud.aluno.DisciplinaClient: DEBUG
```
- Configure um novo comportamento para timeout de conexões para as requisições
```java
  int FIVE_SECONDS = 5000; // milliseconds
  @Bean
  public Request.Options options() {
      return new Request.Options(FIVE_SECONDS, FIVE_SECONDS);
  }
```
- Habilite também a compressão da requisição e resposta à ser realizada
```
feign.compression.request.enabled=true
feign.compression.response.enabled=true
```
- Execute e teste a aplicação
