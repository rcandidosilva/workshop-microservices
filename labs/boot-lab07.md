# Laboratório 7

## Objetivos
- Persistindo NoSQL com Spring Data MongoDB

## Tarefas
### Persista dados em um NoSQL com Spring Data MongoDB
- Crie um novo projeto Spring Boot
- Configure a dependência do Spring Data MongoDB 
```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-mongodb</artifactId>
  </dependency>
```
- Realize a instalação do MongoDB no seu ambiente local
  - Windows: https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/
  - Linux: https://docs.mongodb.com/manual/administration/install-enterprise-linux/
  - OSX: https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/
  - MLab: https://mlab.com/
  - Docker: https://hub.docker.com/_/mongo/ 
- Implemente uma classe com mapeamentos MongoDB para definir informações de auditoria
```java
@Document
class Auditoria {
  @Id
  String uuid;
  String acao;
  String usuario;
  Date timestamp;
  // getters/setters
}
```
- Defina o repositório `AuditoriaRepository` extendendo do `MongoRepository`
- Defina um REST controller para `salvar` e `listar` os dados de auditoria
- Injete o `AuditoriaRepository` no REST controller definido
- Implemente os métodos para `salvar` e `listar` os dados
- Execute e teste a aplicação

### Implemente consultas customizadas no repositório NoSQL
- Utilize o projeto definido anteriormente
- Defina uma consulta utilizando o mecanismo de consultas dinâmicas (definidas pelo nome do método) para retornar os registros de auditoria que aconteceram após um determinado timestamp (informado via parâmetro)
- Implemente um novo endpoint REST para executar a nova consulta por timestamp definida
- Defina uma nova consulta utilizando `@Query` para retornar os registros de auditoria de um determinado usuário
- Implemente um novo endpoint REST para executar a nova consulta por usuario definida
- Execute e teste a aplicação 

### Implemente uma classe de teste para o repositório NoSQL
- Utilize o projeto defindo anteriormente
- Implemente uma classe de teste para o `AuditoriaRepository`
- Utilize a configuração `@SpringBootTest` para a implementação do unit test
- Injete o repositório para poder realizar as chamadas e as asserções do teste
- Implemente um teste unitário para cada método utilizado pelo repositório na aplicação
- Execute a classe de teste com sucesso
