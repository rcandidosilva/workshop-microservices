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
- Implemente uma Feign interface para recuperar os alunos no projeto `disciplina-service`
- Implemente um REST endpoint para consultar e retornar o DTO da aluno com as disciplinas
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
