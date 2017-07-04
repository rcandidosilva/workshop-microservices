# Laboratório 08

## Objetivos
- Monitorando os circuitos com Hystrix Dashboard e Turbine

## Tarefas

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
  - Não se esqueça de adicionar a dependência do `spring-cloud-starter-config`
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
- Execute e teste a aplicação
  - http://localhost:7979/hystrix
  - Para monitorar circuitos de algum serviço, é necessário adicionar a seguinte URL na tela de configuração
    - http://localhost:8080/hystrix.stream (`aluno-service`)
    - http://localhost:8081/hystrix.stream (`disciplina-service`)
  - É necessário realizar alguns acessos aos circuitos para ativar o monitoramento

### Monitore todos os circuit breakers via Turbine
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

### Otimize o monitoramento dos circuit breakers com Turbine Stream
- Utilize os projetos definidos anteriormente
- Crie um novo projeto Spring Boot para representar o Turbine Stream
  - Apenas adicione a dependência do `spring-cloud-starter-config`
    - Não adicione nenhuma outra dependência (ex: `spring-cloud-starter-eureka`)
- Adicione a dependência `spring-cloud-starter-turbine-stream` neste novo projeto
```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-turbine-stream</artifactId>
    </dependency>
```
- Adicione também a dependência `spring-cloud-starter-stream-rabbit` neste projeto
```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
    </dependency>
```
- Adicione a anotação `@EnableTurbineStream` na classe `Application`
```java
@EnableTurbineStream
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
- Adicione a configuração do serviço Turbine Stream no Config Server
```
server:
  port: ${PORT:8989}
```
- Não se esqueça de configurar o `bootstrap.yml` na aplicação para se conectar com o Config Server
```
spring:
  application:
    name: turbine-stream
  cloud:
    config:
      uri: http://localhost:8888  
```
- Nos projetos dos serviços clientes, será necessário adicionar as dependências do `spring-cloud-netflix-hystrix-stream` e `spring-cloud-starter-stream-rabbit`
```xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-netflix-hystrix-stream</artifactId>
  </dependency>
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
  </dependency>
```
- Será necessário utilizar o `RabbitMQ` para rodar este exemplo. Inicie o serviço do middleware, ou realize uma nova instalação, caso não o tenha instalado
  - Windows
    - https://www.rabbitmq.com/install-windows.html
  - Linux
    - `apt-get install rabbitmq-server`
    - `yum install rabbitmq-server`
  - Mac OS
    - `brew install rabbitmq`
- Execute e teste a aplicação
  - http://localhost:7979/hystrix
  - Para verificar o stream funcionando será necessário configurar a URL do Turbine Stream no Hystrix DashBoard
    - http://localhost:8989
  - Verifique também as mensagens sendo enviadas e processadas na fila do `RabbitMQ`
