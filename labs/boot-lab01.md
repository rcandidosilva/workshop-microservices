# Laboratório 1

## Objetivos
- Criar e executar uma primeira aplicação com Spring Boot

## Tarefas
### Criando o projeto
- Acesse o site Spring Initializr: http://start.spring.io/
- Selecione o tipo Maven Project.
- Selecione o empacotamento como JAR
- Selecione a linguagem como Java
- Adicione a dependência Web
- Clique em gerar projeto
- Descompacte o projeto gerado em um diretório
- Importe o projeto na IDE (Import -> Existing Maven Projects)

### Executando o projeto via IDE
- Abra a classe Application e execute como uma aplicação Java 
  - Run As -> Java Application
- Você pode rodar também utilizando o Spring Boot Dashboard (STS)

### Executando o projeto via Maven plugin
- Acesse um terminal ou via IDE e execute o seguinte Maven goal
  - `mvn spring-boot:run`

### Executando o projeto via Java
- Realize a compilação e o empacotamento do projeto via Maven
  - `mvn install`
- Acesse o diretório /target gerado e execute o JAR produzido
  - `java -jar [your project]-0.0.1-SNAPSHOT.jar`

### [OPCIONAL]: Modifique o container Web embutido pela aplicação
- Modifique o `pom.xml` da aplicação para modificar o container default `Tomcat` pelo `Undertow` 
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-undertow</artifactId>
</dependency>
```
- Execute novamente a aplicação

### [OPCIONAL]: Executando o projeto em um container via WAR package file
- Modifique o `pom.xml` da aplicação para realizar deployment via WAR file
```xml
<project>
    <!-- ... -->
    <packaging>war</packaging>
    <!-- ... -->
    <dependencies>
       <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
            <scope>provided</scope>
        </dependency>
        <!-- ... -->
    </dependencies>
</project>
```
- Implemente o `SpringBootServletInitializer` na aplicação Spring Boot
```java
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
```
- Realize o deployment em um container Web (ex: Tomcat)
  - Você precisará realizar o download, instalação e configuração do container Web
    - http://tomcat.apache.org/download-80.cgi
