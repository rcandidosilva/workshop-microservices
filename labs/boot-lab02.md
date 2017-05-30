# Laboratório 2

## Objetivos
- Explorando recursos do Spring Boot

## Tarefas
### Manipule propriedades utilizando @Value e Spring Environment
- Crie um novo projeto Spring Boot
- Defina algumas propridades customizadas no arquivo `application.properties`
- Defina um novo Bean na sua aplicação e utilize a anotação `@Value` para injetar e propriedade configurada
- Realize a injeção de dependência do objeto `Environment` no Bean da sua aplicação
- Acesse alguma outra propriedade definida por meio do objeto `Environment` injetado
- Execute a aplicação imprimindo o valor das propriedades configuradas
  - Se possível, utilize o mecanismo de `Logging` para imprimir as propriedades.

### Utilize diferentes Profiles
- Utilizando o projeto anteriormente definido, defina diferentes arquivos de propriedades para diferentes perfils de aplicação (ex: dev, prod)
  - `application-dev.properties`
  - `application-prod.properties`
- Defina valores diferentes para as propriedades definidas em cada arquivo
- Execute a aplicação e verifique o valor das propriedades definidas
- Execute novamente a aplicação informando um diferente perfil de execução
  - `java -Dspring.profiles.active=dev -jar application.jar`

### Configure o Actuator
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

### Configure o DevTools
- Adicione a dependência `spring-boot-dev-tools` no seu projeto
```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
```
- Execute a aplicação
- Com a aplicação rodando, realize uma modificação em algum arquivo da aplicação, por exemplo adicionando um comentário, um espaçamento em uma classe, etc.
- Observe que dependendo da mudança realizada, o DevTools irá reiniciar automaticamente sua aplicação. 
- Instale o plug-in do LiveReload no browser e teste esse comportamento.
