# Laboratório 02

## Objetivos
- Replicando a configuração centralizada com Spring Cloud Config Bus

## Tarefas

### Ajuste a configuração do Config Server
- Utilize o projeto definido no exercício anterior
- Adicione a dependência `spring-cloud-config-monitor` e do `spring-cloud-starter-stream-rabbit` no projeto do config server
```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-monitor</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
    </dependency>
```
- Realize a instalação do `RabbitMQ` 
  - Windows
    - https://www.rabbitmq.com/install-windows.html
  - Linux
    - apt-get install rabbitmq-server
    - yum install rabbitmq-server
  - Mac OS
    - brew install rabbitmq
- Configure o suporte ao barramento no config server
```
server:
  port: 8888

management:
  security:
    enabled: false

spring:
  cloud:
    config:
      server:
        git:
          uri: file://local/git/config-repo        
    bus:
      enabled: true
  rabbitmq:
    host: localhost
    port: 5672          
```
### Ajuste a configuração do Config Client
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
- Adicione a dependência `spring-cloud-starter-config` no seu projeto
```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
```
- Crie o arquivo `bootstrap.yml` no diretório `src/main/resources` da aplicação
- Configure as seguintes propriedades no arquivo Boostrap
```
spring:
  application:
    name: cloud-lab01
  cloud:
    config:
      uri: http://localhost:8888
```
- Execute e teste a aplicação

### Utilize propriedades externas configuradas
- Utilize o projeto cliente definido anteriormente
- Modifique o arquivo `cloud-lab01.yml` definido no repositório Git adicionando algumas propriedades customizadas
- Não se esqueça de comitar o arquivo modificado no repositório Git
```
$ git add .
$ git commit -m "Changes at cloud-lab01"
```
- Defina um novo REST controller na sua aplicação e crie um endpoint REST para imprimir as propriedades configuradas 
  - Não se esqueça de definir `@RefreshScope` como escopo do controller
- Utilize a anotação `@Value` e\ou objeto `Environment` para acessar as propriedades configuradas
- Execute e teste a aplicação visualizando o valor das propriedades configuradas
- Com a aplicação rodando, modifique alguma propriedade no repositório Git
- Realize um HTTP POST no `/refresh` endpoint da aplicação cliente definida
  - POST http://localhost:8081/refresh
- Execute novamente o REST endpoint definido e identifique as propriedades modificadas

### Manipule diferentes profiles
- Utilize os projetos definidos anteriormente
- Modifique o arquivo `cloud-lab01.yml` incluindo diferentes profiles, e definido valores diferentes nas propriedades customizadas para cada profile definido
```
server:
  port: ${PORT:8081}
  
---
spring:
  profiles: dev
```
- Execute a aplicação informando um diferente perfil de execução.
  - `java -Dspring.profiles.active=dev -jar application.jar`