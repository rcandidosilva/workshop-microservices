# Laboratório 03

## Objetivos
- Implementando balanceamento de carga e tolerância à falhas com Netflix Ribbon

## Tarefas

### Implemente Ribbon client (sem o Eureka server)
- Utilize os projetos definidos no exercício anterior
- Adicione a dependência `spring-cloud-starter-ribbon` no projeto do `aluno-service`
```xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-ribbon</artifactId>
  </dependency>
```
- Adicione a configuração para o Ribbon client acessar o `disciplina-service` nas propriedades do serviço de `Aluno`
  - DICA: Defina um novo profile `ribbon-only` para usar esta configuração
```
...
---
spring:
  profiles: ribbon-only

server:
  port: ${PORT:8080}

disciplina-service:
  ribbon:
    eureka:
      enabled: false
    listOfServers: localhost:8081,localhost:18081,localhost:28081
    ServerListRefreshInterval: 15000
```
- Para desabilitar totalmente o Eureka Server no `aluno-service`, será necessário comentar a anotação `@EnabledDiscoveryClient` na aplicação
- Defina uma classe para configuração do Ribbon client a ser utilizado
```java
  public class RibbonConfiguration {

      @Autowired IClientConfig ribbonClientConfig;

      @Bean
      public IPing ribbonPing(IClientConfig config) {
          return new PingUrl();
      }

      @Bean
      public IRule ribbonRule(IClientConfig config) {
          return new AvailabilityFilteringRule();
      }
  }
```
- Adicione a configuração do `@RibbonClient` no REST controller da aplicação, ou no local aonde será utilizado o serviço destino
```java
@RestController
@RibbonClient(name = "disciplina-service", configuration = RibbonConfiguration.class)
public class AlunoRestController {
  // ...
}
```

- Configure um bean `RestTemplate` com a anotação `@LoadBalanced` na aplicação
```java
  @LoadBalanced @Bean
  RestTemplate restTemplate(){
      return new RestTemplate();
  }
```
- Implemente um objeto DTO para representar as informações do `Aluno` e das disciplinas matriculadas
```java
class AlunoDTO {
  String nome;
  String email;
  Integer matricula;
  List<String> disciplinas;
  // getters/setters
}
```
- Implemente um REST endpoint para consultar e retornar o DTO do aluno com as disciplinas
- Para acessar o serviço de disciplinas, utilize o `RestTemplate` configurado anteriormente
```java
    @Autowired RestTemplate
    //...
    restTemplate.getForEntity("http://disciplina-service/disciplinas/nomes",
        List.class);
```
- Execute e teste a aplicação com apenas uma instância do `disciplina-service`
- Experimente subir mais de uma instância do `disciplina-service` e teste a aplicação
  - DICA: Para subir mais de uma instância utilize a variável de ambiente `-Dserver.port`
    - `spring-boot:run -Dserver.port=18081`
- Execute novamente a aplicação e observe qual a(s) instância(s) que são localizadas durante a execução
  - DICA: Ative o `spring.jpa.show-sql` para facilitar a identificação de qual instância está sendo executada
- Experimente derrubar uma instância do `disciplina-service` durante a execução e teste o mecanismo de tolerância à falhas
  - A configuração Ribbon client conseguiu identificar e recuperar essa falha?

### Implemente Ribbon client (com o Eureka server)
- Utilize os projetos definidos no exercício anterior
- Adicione a dependência `spring-cloud-starter-ribbon` no projeto do `disciplina-service`
```xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-ribbon</artifactId>
  </dependency>
```
- Configure um bean `RestTemplate` com a anotação `@LoadBalanced` na aplicação, caso não esteja configurado
```java
  @LoadBalanced @Bean
  RestTemplate restTemplate(){
      return new RestTemplate();
  }
```
- Verifique se os projetos `disciplina-service` e `aluno-service` estão registrando-se corretamente no Eureka Server
  - DICA: Habilite novamente o `@EnableDiscoveryClient` no `aluno-service`, caso tenha desabilitado no exercício anterior
- Modifique a implementação do REST endpoint para retornar o `DisciplinaDTO` para buscar os alunos via `RestTemplate`
  - Neste caso, não deve-se utilizar a configuração `ribbon.listOfServers` nas propriedades do serviço de `Disciplina`
  - Essa lista deverá ser retornada dinâmicamente do registro no Eureka Server
- Execute e teste a aplicação
  - Realize os mesmos testes de execução com múltiplas instâncias e tolerância à falhas realizado no exercício anterior
