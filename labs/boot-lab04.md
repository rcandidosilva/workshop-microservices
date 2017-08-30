# Laboratório 4

## Objetivos
- Persistindo dados com Spring Data JPA

## Tarefas
### Persista dados em um RDBMS com Spring Data JPA
- Utilize o projeto criado no exercício anterior, ou crie um novo projeto Spring Boot
- Configure a dependência do Spring Data JPA
```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
```
- Implemente os mapeamentos JPA na classe `Aluno`
```java
@Entity
class Aluno {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;
  String nome;
  Integer matricula;
  String email;
  Date dataNascimento;
  // getters/setters
}
```
- Defina o repositório `AlunoRepository` extendendo do `JpaRepository`
- Injete o `AlunoRepository` no REST controller do aluno
- Modifique os métodos do REST controller para persistir e/ou recuperar os dados do aluno
- Adicione a dependência do banco de dados H2
```xml
  <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <scope>runtime</scope>
  </dependency>
```
- Ative o Web console de administração do H2, e a URL para conexão adicionando as seguintes propriedades no `application.properties`
```
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:alunodb
```
- Execute e teste a aplicação

### Implemente consultas customizadas no repositório JPA
- Utilize o projeto definido anteriormente
- Defina uma consulta utilizando o mecanismo de consultas dinâmicas (definidas pelo nome do método) para retornar os alunos que contém um determinado nome
- Implemente um novo endpoint REST para executar a nova consulta por nome definida
- Defina uma nova consulta utilizando `@Query` para retornar os alunos cujo mês de nascimento é igual ao mês corrente
- Implemente um novo endpoint REST para executar a nova consulta por mês de nascimento definida
- Execute e teste a aplicação

### Configure e utilize o ProjectLombok
- Utilize o projeto definido anteriormente
- Instale o plug-in do ProjectLombok na IDE
  - https://projectlombok.org/setup/eclipse
- Configure a dependência do ProjectLombok

```xml
  <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.16.16</version>
  </dependency>  
```
- Modifique a implementação da entidade `Aluno` para utilizar ProjectLombok
  - DICA: Utilize as anotações `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder`, `@EqualsAndHashcode`, `@ToString`
- Execute e teste a aplicação

### [OPCIONAL]: Implemente uma classe de teste para o repositório JPA
- Utilize o projeto defindo anteriormente
- Implemente uma classe de teste para o `AlunoRepository`
- Utilize a configuração `@DataJpaTest` para facilitar a implementação do unit test
- Injete o repositório e o `TestEntityManager` para poder realizar as chamadas e as asserções do teste
- Implemente um teste unitário para cada método utilizado pelo repositório na aplicação
- Execute a classe de teste com sucesso
