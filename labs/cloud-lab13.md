# Laboratório 13

## Objetivos
- Roteando os serviços com Netflix Zuul

## Tarefas

### Defina um API gateway server com Zuul
- Utilize os projetos definidos no exercício anterior
- Crie um novo projeto Spring Boot para representar o API gateway com Zuul `zuul-server`
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
- Adicione as seguintes dependências no projeto
  - `spring-boot-starter-web`
  - `spring-boot-starter-actuator`
  - `spring-cloud-starter-config`
- Adicione também a dependência `spring-cloud-starter-zuul` no seu projeto
```xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-zuul</artifactId>
  </dependency>
```
- Configure o serviço do Zuul adicionando a anotação `@EnableZuulProxy` na aplicação
```java
  @EnableZuulProxy
  @SpringBootApplication
  public class ZuulServerApplication {

	    public static void main(String[] args) {
		      SpringApplication.run(ZuulServerApplication.class, args);
      }
  }
```
- Adicione a configuração do Zuul no Config Server definindos as rotas utilizando URLs
```
server:
  port: ${PORT:8000}

zuul:
    routes:
      aluno-service:
        path: /aluno/**
        url: http://localhost:8080
      disciplina-service:
        path: /disciplina/**
        url: http://localhost:8081  
```
- Não se esqueça de configurar o `bootstrap.yml` na aplicação para se conectar com o Config Server
```
spring:
  application:
    name: zuul-server
  cloud:
    config:
      uri: http://localhost:8888
      username: configUser
      password: configPassword
```
- Execute e teste a aplicação
  - Tente acessar os serviços do `aluno-service` e `disciplina-service` via Zuul proxy server
    - http://localhost:8000/aluno/alunos/1
    - http://localhost:8000/disciplina/disciplinas/1
  - Foi possível realizar o acesso aos serviços? O que aconteceu?
- Adicione a seguinte configuração no Zuul proxy server

### Utilize o Ribbon para balanceamento com Zuul
- Utilize os projetos definidos anteriormente
- Adicione a dependência do Netflix Ribbon no projeto `zuul-server`
```xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-ribbon</artifactId>
	</dependency>
```
- Adicione as seguintes configurações nas propriedades do Zuul no Config Server
```
ribbon:
  eureka:
    enabled: false
```
- Configure os serviços Ribbon para roteamento do `aluno-service` e `disciplina-service` nas propriedades
```
aluno-service:
  ribbon:
    listOfServers: localhost:8080,localhost:18080

disciplina-service:
  ribbon:
    listOfServers: localhost:8081,localhost:18081
```    
- Ajuste também as configurações de roteamento do Zuul nas propriedades
```
zuul:
  routes:
    aluno-service:
      path: /aluno/**
      serviceId: aluno-service
    disciplina-service:
      path: /disciplina/**
      serviceId: disciplina-service
```
- Execute e teste a aplicação
  - Execute duas instâncias do `aluno-service` e também do `disciplina-service` nas portas configuradas
    - `mvn spring-boot:run -DPORT=[port]`
  - Acesse os REST endpoints roteados e verifique o balanceamento de carga sendo realizado
    - http://localhost:8000/aluno/alunos/1
    - http://localhost:8000/disciplina/disciplinas/1

### Utilize o Eureka para descoberta dos serviços com Zuul
- Utilize os projetos definidos anteriormente
- Adicione a dependência do Netflix Ribbon no projeto `zuul-server`
```xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-eureka</artifactId>
  </dependency>
```
- Ative a utilização do Eureka habilitando a seguinte configuração nas propriedades do Zuul no Config Server
```
ribbon:
  eureka:
    enabled: true
```
- Remova também as configurações dos serviços Ribbon do `aluno-service` e `disciplina-service` realizados anteriormente nas propriedades
- Execute e teste a aplicação
  - Execute duas instâncias do `aluno-service` e também do `disciplina-service` utilizando portas diferentes da configuradas via Ribbon
    - `mvn spring-boot:run -DPORT=[port]`
  - Acesse os REST endpoints roteados e verifique a descoberta dos serviços sendo realizada
    - http://localhost:8000/aluno/alunos/1
    - http://localhost:8000/disciplina/disciplinas/1  
