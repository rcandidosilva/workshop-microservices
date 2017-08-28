# Laboratório 5

## Objetivos
- Expondo os repositórios com Spring Data REST

## Tarefas
### Exponha as funções do repositório como endpoints REST
- Utilize o projeto criado no exercício sobre Spring Data JPA, ou crie um novo projeto
- Configure a dependência do Spring Data JPA, se necessário
```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
```
- Configure a dependência do Spring Data REST
```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-rest</artifactId>
  </dependency>
```
- Implemente uma nova entidade para definir as informações da `Disciplina`
```java
@Entity
class Disciplina {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;
  String nome;
  Integer cargaHoraria;
  Date dataInicio;
  // getters/setters
}
```
- Implemente o repositório `DisciplinaRepository` extends do `JpaRepository`
- Adicione a anotação `@RepositoryRestResource` configurando um novo REST path, se desejado
- Execute e teste a aplicação

### Implemente consultas customizadas no repositório
- Utilize o projeto definido anteriormente
- Defina uma nova consulta para retornar as disciplinas com a data de início maior que a data atual
- Configure a anotação `@RestResource` para expor e customizar a publicação do REST endpoint de consulta
- Execute e teste a aplicação

### Habilite o HAL browser na aplicação
- Utilize o projeto defindo anteriormente
- Configure a dependência do HAL browser na aplicação
```xml
  <dependency>
      <groupId>org.springframework.data</groupId>
      <artifactId>spring-data-rest-hal-browser</artifactId>
  </dependency>
```
- Execute e teste a aplicação
  - http://localhost:8080
