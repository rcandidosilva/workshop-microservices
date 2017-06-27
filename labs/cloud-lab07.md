# Laboratório 07

## Objetivos
- Implementando circuit breakers com Netflix Hystrix

## Tarefas

### Implemente circuit breaker via @HystrixCommand
- Utilize os projetos definidos no exercício anterior
- Adicione a dependência `spring-cloud-starter-hystrix` no projeto do `aluno-service`
```xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-hystrix</artifactId>
  </dependency>
```
- Ative a configuração do Hystrix utilizando a anotação `@EnableCircuitBreaker` na aplicação
```java
  @@EnableCircuitBreaker
  @SpringBootApplication
  public class Application {
      public static void main(String[] args) {
          SpringApplication.run(Application.class, args);
      }
  }
```
- Implemente uma classe `DisciplinaServiceProxy` para controlar as chamadas do `aluno-service` -> `disciplina-service`
```java
@Service
public class DisciplinaServiceProxy {
    // TODO
}
```
- Injete a interface Feign `DisciplinaClient` via `@Autowired` em um atribute na classe proxy
- Implemente um método para recuperar os nomes das disciplinas via `disciplina-service` utilizando a `DisciplinaClient`
- Implemente um método fallback para recuperar nomes de disciplinas caso o `disciplina-service` esteja indisponível
- Utilize a anotação `@HystrixCommand` para configurar um circuit breaker na chamada para os métodos de recuperação dos nomes de disciplinas
```java
  @HystrixCommand(fallbackMethod = "getNomesDisciplinasFallback")
  public List<String> getNomesDisciplinas() {
      // ...
  }
```
- Injete um objeto da classe `DisciplinaServiceProxy` no REST controller, e utilize-o para acessar os nomes das disciplinas
- Execute e teste a aplicação

### Implemente um circuit breaker via Feign Hystrix fallback
- Utilize os projetos definidos anteriormente
- Adicione a dependência `spring-cloud-starter-hystrix` no projeto da `disciplina-service`
```xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-hystrix</artifactId>
  </dependency>
```
- Ative a configuração do Hystrix utilizando a anotação `@EnableCircuitBreaker` na aplicação
```java
  @@EnableCircuitBreaker
  @SpringBootApplication
  public class Application {
      public static void main(String[] args) {
          SpringApplication.run(Application.class, args);
      }
  }
```
- Implemente uma classe `AlunoClientFallback` fornecendo uma implementação de fallback para cada método definido na interface Feign
```java
  @Component
  public class AlunoClientFallback implements AlunoClient {
      // TODO
  }
```
- Configure a implementação de fallback definida na interface Feign do `AlunoClient`
```java
  @FeignClient(name = "aluno-service", fallback = AlunoClientFallback.class)
  public interface AlunoClient {
      // ...
  }
```
- Configure a propriedade `feign.hystrix.enabled` nas configurações da `disciplina-service`
```
feign:
  hystrix:
    enabled: true
```
- Execute e teste a aplicação
