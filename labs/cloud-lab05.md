# Laboratório 05

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
- Injete a interface Feign `DisciplinaClient` via `@Autowired` na classe proxy
- Implemente um método para recuperar os nomes das disciplinas via `disciplina-service` utilizando a interface Feign `DisciplinaClient`
- Implemente um fallback método para recuperar nomes das disciplinas caso o `disciplina-service` esteja indisponível
- Utilize a anotação `@HystrixCommand` para configurar um circuit breaker na chamada do método de recuperação dos nomes das disciplinas
```java
  @HystrixCommand(fallbackMethod = "getNomesDisciplinasFallback")
  public List<String> getNomesDisciplinas() {
      // ...
  }
```
- Configure as seguintes propriedades para o Hystrix Command utilizando anotação `@HystrixProperty`
  - `execution.isolation.strategy` = THREAD
  - `circuitBreaker.requestVolumeThreshold` = 5
  - `requestCache.enabled` = false
- Configure também as seguintes propriedades para o comportamento do Hystrix thread pool
  - `coreSize` = 5
  - `maximumSize` = 5
```java
@HystrixCommand(fallbackMethod = "getNomesDisciplinasFallback",
  commandProperties = {
      @HystrixProperty(name="execution.isolation.strategy", value="THREAD"),
      @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),
      @HystrixProperty(name="requestCache.enabled", value="false"),
  },threadPoolProperties = {
      @HystrixProperty(name="coreSize", value="5"),
      @HystrixProperty(name="maximumSize", value="5")
  })
```
- Injete o objeto `DisciplinaServiceProxy` no REST controller, e utilize para acessar os nomes das disciplinas
- Execute e teste a aplicação
  - Tente realizar mais de 5 chamadas consecutivas ao circuito definido
  - Verifique se o circuito foi aberto acessando o `/health` status do serviço
    - http://localhost:8080/health
    - Caso não apareça as informações, tente desabilitar a proteção de segurança via `management.security.enabled` = false     


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
- Implemente uma classe `AlunoClientFallback` fornecendo uma implementação de fallback para cada método definido na interface Feign `AlunoClient`
```java
  @Component
  public class AlunoClientFallback implements AlunoClient {
      // TODO
  }
```
- Configure a implementação deste fallback na interface Feign do `AlunoClient`
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
- Configure as seguintes propriedades para o Hystrix Command nas propriedades do Config Server
  - `execution.isolation.strategy` = SEMAPHORE
  - `execution.isolation.semaphore.maxConcurrentRequests` = 5
  - `fallback.isolation.semaphore.maxConcurrentRequests` = 5
  - `circuitBreaker.requestVolumeThreshold` = 5
```
hystrix:
  command:
    AlunoClient#getAllAlunos():
      execution:
        isolation:
          strategy: SEMAPHORE
          semaphore:
            maxConcurrentRequests: 5
      fallback:
        isolation:
          semaphore:
            maxConcurrentRequests: 5
      circuitBreaker:
        requestVolumeThreshold: 5
```
- Execute e teste a aplicação

### Monitore um circuit breaker com Hystrix Dashboard
- Utilize os projetos definidos no exercício anterior
- Crie um novo projeto Spring Boot para representar o Hystrix Dashboard
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
- Adicione a dependência `spring-cloud-starter-hystrix-dashboard` no seu projeto
```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
    </dependency>
```
- Adicione a anotação `@EnableHystrixDashboard` na classe `Application`
```java
@EnableHystrixDashboard
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
- Adicione a configuração do novo serviço Hystrix Dashboard no Config Server
```
server:
  port: ${PORT:7979}

eureka:
  client:
    serviceUrl:
      defaultZone: ${EUREKA_URI:http://localhost:8761/eureka}
  instance:
    preferIpAddress: true
```
- Não se esqueça de configurar o `bootstrap.yml` na aplicação para se conectar com o Config Server
```
spring:
  application:
    name: hystrix-dashboard
  cloud:
    config:
      uri: http://localhost:8888  
```
- Adicione também a dependência do `spring-cloud-starter-config` no projeto
- Execute e teste a aplicação
  - http://localhost:7979/hystrix
  - Para monitorar circuitos de algum serviço, é necessário adicionar a seguinte URL na tela de configuração
    - http://localhost:8080/hystrix.stream (`aluno-service`)
    - http://localhost:8081/hystrix.stream (`disciplina-service`)
  - É necessário realizar alguns acessos aos circuitos para ativar o monitoramento

### [OPCIONAL]: Monitore todos os circuit breakers via Turbine
- Utilize os projetos definidos anteriormente
- Adicione a dependência `spring-cloud-starter-turbine` no projeto do `hystrix-dashboard`
```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-turbine</artifactId>
    </dependency>
```
- Adicione a anotação `@EnableTurbine` na classe `Application`
```java
@EnableTurbine
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
- Configure o cluster Turbine para monitoramento nas propriedades da aplicação
```
turbine:
  appConfig: aluno-service,disciplina-service
  clusterNameExpression: "'default'"    
```
- Execute e teste a aplicação
  - http://localhost:7979/hystrix
  - Utilize a seguinte URL na configuração do dashboard para monitorar todos os circuitos dos serviços no cluster
    - http://localhost:7979/turbine.stream
