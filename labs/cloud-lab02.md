# Laboratório 02

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

### Registre um Eureka Client
- Crie um nova aplicação Spring Boot para representar um serviço de alunos
- Implemente uma classe para representar o objeto `Aluno`
```java
class Aluno {
  Long id;
  String nome;
  Integer matricula;
  String email;
  // getters/setters
}
```
- Implemente um `JPA repository`, e expõa o repositório como um REST controller
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
- Adicione a anotação `@EnableDiscoveryClient` no classe de aplicação do Spring Boot
```java
@EnableDiscoveryClient
@SpringBootApplication
public class AlunoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlunoApplication.class, args);
    }
}
```
- Configure a conexão entre o cliente e o Eureka server no arquivo `application.yml`
```
spring:
  application:
    name: aluno-service

eureka:
  client:
    serviceUrl:
      defaultZone: ${EUREKA_URI:http://localhost:8761/eureka}
  instance:
    preferIpAddress: true
```
- Execute e teste a aplicação, verificando o registro do cliente no Eureka server
  - http://localhost:8761

### Localize um serviço via Eureka
- Crie um nova aplicação Spring Boot para representar um serviço de disciplinas
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
- Implemente um objeto DTO para representar as informações da `Disciplina` e alunos matriculados
```java
class DisciplinaDTO {
  String nome;
  Integer cargaHoraria;
  Date dataInicio;
  List<String> alunosMatriculados;
  // getters/setters
}
```
- Implemente um REST endpoint para consultar e retornar o DTO da disciplina e alunos
- Realize uma localização no Eureka para buscar os alunos por meio do serviço `aluno-service` registrado anteriormente
  - DICA: Verifique o exemplo nos slides para realizar uma localização de serviços no Eureka utilizando o objeto `DiscoveryClient`
- Execute e teste a aplicação
- Experimente subir mais de uma instância do serviço de alunos e verifique-os registrados no Eureka server
- Execute novamente a aplicação e observe qual a(s) instância(s) que são localizadas durante a execução

### Integre os projetos via Config Server
- Utilize o projeto e repositório Git do config server definido nos exercícios anteriores
- Crie e configure os arquivos `eureka-service.yml`, `aluno-service.yml` e `disciplina-service.yml`
- Não se esqueça de adiciona-los e comitá-los no repositório Git
```
$ git add .
$ git commit -m "Added files for cloud-lab03"
```
- Altere os projetos Spring Boot criados anteriormente, excluindo os arquivos `application.yml` e criando os arquivos `bootstrap.yml`
```
spring:
  application:
    name: [service-name]
  cloud:
    config:
      uri: http://localhost:8888
```
- Execute o config server e reinicie todos os demais serviços
- Teste e analise o comportamento da aplicação
