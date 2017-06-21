# Laboratório 03

## Objetivos
- Registrando e descobrindo serviços com Netflix Eureka

## Tarefas

### Configure o Eureka Server
- Crie uma nova aplicação Spring Boot
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
- Adicione a dependência `spring-cloud-starter-eureka-server` no seu projeto
```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-eureka-server</artifactId>
    </dependency>
```
- Adicione a anotação `@EnableEurekaServer` na classe `Application`
- Configure as definições do serviço Eureka no `application.yml` da aplicação Spring Boot criada
```
server:
  port: 8761

eureka:
  instance:
    hostname: localhost
  client:
    registerWithEureka: false
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```
- Execute a aplicação e acesse o Eureka Dashboard Web
  - http://localhost:8761

### Configure o Eureka Client
- Crie um nova aplicação Spring Boot
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
- Adicione a dependência `spring-cloud-starter-eureka` no seu projeto
```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
	      <artifactId>spring-cloud-starter-eureka</artifactId>
    </dependency>
```
- Configure a conexão entre o cliente e o Eureka server no arquivo `application.yml`
```
spring:
  application:
    name: spring-cloud-eureka-client

eureka:
  client:
    serviceUrl:
      defaultZone: ${EUREKA_URI:http://localhost:8761/eureka}
  instance:
    preferIpAddress: true
```
- Execute e teste a aplicação, verificando o registro do cliente no Eureka server
  - http://localhost:8761

### Integre os projetos no Config Server
- 
