# Laboratório 2

## Objetivos
- Explorando demais recursos do Spring Boot

## Tarefas

## Configure o Actuator
- Adicione a dependência `spring-boot-starter-actuator` no seu projeto
```xml
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-actuator</artifactId>
	</dependency>
```
- Execute a aplicação 
- Abra a aplicação em um browser e verifique os seguintes endpoints
  - http://localhost:8080/info
  - http://localhost:8080/health
- Tente acessar os seguintes demais endpoints
  - http://localhost:8080/beans
  - http://localhost:8080/configprops
  - http://localhost:8080/autoconfig
  - http://localhost:8080/mappings
- Foi possível acessa-los? Por padrão o Spring Boot não libera o acesso de alguns endpoints. É necessário habilitá-los via configurações especiais de segurança.
- Para desabilitar essa proteção, adicione a seguinte propriedade no arquivo `application.properties`
  - `management.security.enabled = false`
- Reinicie a aplicação e tente acessar novamente os endpoints protegidos

## Configure o DevTools