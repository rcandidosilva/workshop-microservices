# Spring Cloud - Laboratório 01

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

spring:
  cloud:
    config:
      server:
        git:
          uri: file://local/git/config-repo
```
- Adicione os seguintes arquivos de configuração no repositório Git criado
**application.yml**
```
server:
  port: ${PORT:${SERVER_PORT:0}}

info:
  id: ${spring.application.name}

logging:
  level: debug
```
**cloud-lab01.yml**
```
server:
  port: ${PORT:8081}
  
logging:
  level: debug  
```
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
- Adicione a dependência do Spring Cloud no `pom.xml`
```xml
    <dependencyManagement>
        <dependencies>
	    <dependency>
	        <groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-dependencies</artifactId>
		<version>Dalston.RELEASE</version>
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
- Crie o arquivo `bootstrap.properties` no diretório `src/main/resources` da aplicação
- Configure as seguintes propriedades no arquivo Boostrap
```
  spring.application.name=cloud-lab01
  spring.cloud.config.uri=http://localhost:8888
```
- Adicione um REST controller para recuperar valores de propriedades configuradas
- Execute a aplicação e verifique a propriedade sendo demonstrada

### [OPCIONAL]: Trabalhando com Spring Config e Profiles
- TODO

## Perguntas
- Note que o cliente necessita apenas de algumas dependências do Spring Cloud e da configuração da URI do Config Server. Não é necessário código customizado para acesso.
- O que acontece se o Config Server ficar indisponível quando a aplicação cliente iniciar?
- O que acontece se é modificada alguma propriedade da aplicação no repositório Git de configurações? 
