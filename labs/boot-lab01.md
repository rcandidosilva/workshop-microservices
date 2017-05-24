# Laboratório 1

## Objetivos
- Criar e executar uma primeira aplicação com Spring Boot

## Tarefas

## Criando o projeto
- Acesse o site Spring Initializr: http://start.spring.io/
- Selecione o tipo Maven Project.
- Selecione o empacotamento como JAR
- Selecione a linguagem como Java
- Clique em gerar projeto
- Descompacte o projeto gerado em um diretório
- Importe o projeto na IDE (Import -> Existing Maven Projects)

## Executando o projeto via IDE
- Abra a classe Application e execute como uma aplicação Java 
-- Run As -> Java Application
- Você pode rodar também utilizando o Spring Boot Dashboard (STS)

## Executando o projeto via Maven plugin
- Acesse um terminal ou via IDE e execute o seguinte Maven goal
-- `mvn spring-boot:run`

## Executando o projeto via Java
- Realize a compilação e o empacotamento do projeto via Maven
-- `mvn install`
- Acesse o diretório /target gerado e execute o JAR produzido
-- `java -jar [your project]-0.0.1-SNAPSHOT.jar`
