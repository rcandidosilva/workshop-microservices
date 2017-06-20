# Laboratório 01

## Objetivos
- Centralizando a configuração com Spring Cloud Config

## Tarefas

### Configure o Config Server
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
- Adicione a dependência `spring-cloud-config-server` no seu projeto
```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-server</artifactId>
    </dependency>
```
- Adicione a anotação `@EnableConfigServer` na classe `Application`
- Crie um novo repositório no Git local para guardar as configurações da sua aplicação
```
$ cd $HOME
$ mkdir config-repo
$ cd config-repo
$ git init .
```
- Configure o acesso ao repositório Git no `application.yml` da aplicação Spring Boot criada
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
```
- Adicione os seguintes arquivos de configuração no repositório Git criado.
  - `application.yml`
  - `cloud-lab01.yml`
  
```
server:
  port: ${PORT:${SERVER_PORT:0}}

info:
  id: ${spring.application.name}

logging:
  level: debug
```
**application.yml**
```
server:
  port: ${PORT:8081}
  
logging:
  level: debug  
```
**cloud-lab01.yml**
- Não se esqueça de adicionar e comitar os novos arquivos no repositório Git
```
$ git add .
$ git commit -m "Initial commit"
```
- Execute a aplicação e acesse os seguintes endereços
  - http://localhost:8888/env
  - http://localhost:8888/cloud-lab01/default

### Configure o Config Client
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
- Adicione a dependência do `spring-boot-starter-actuator`
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
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

## Perguntas
- Note que o cliente necessita apenas de algumas dependências do Spring Cloud e da configuração da URI do Config Server. Não é necessário código customizado para acesso.
- O que acontece se o Config Server ficar indisponível quando a aplicação cliente iniciar?
- O que acontece se é modificada alguma propriedade da aplicação no repositório Git? 
