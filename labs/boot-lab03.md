# Laboratório 3

## Objetivos
- Testando uma aplicação com Spring Boot

## Tarefas
### Implemente um Spring Boot test
- Crie um novo projeto Spring Boot
- Define um novo `@Component` na aplicação Spring Bean para representar uma calculadora
- Implemente métodos para as operações `soma`, `subtração`, `multiplicação`, `divisão`
- Define uma classe de teste utilizando a anotação `@SpringBootTest`
- Realize a injeção do objeto calculadora
- Implemente os métodos de teste unitário para as funções da calculadora
- Execute a classe de teste com sucesso

### Implemente um Spring Boot test utilizando Mockito
- Utilize o projeto anteriormente criado
- Defina uma nova classe de teste utilizando a anotação `@SpringBootTest`
- Realize a injeção do objeto mock da calculadora utilizando `@MockBean`
- Implemente os métodos de teste unitário "mockando" as funções da calculadora
- Execute a classe de teste com sucesso

### Implemente um Spring Boot test para JSON
- Utilize o projeto anteriormente criado
- Implemente uma nova classe para representar um objeto `Aluno`
```java
class Aluno {
  String nome;
  Integer matricula;
  String email;
  // getters/setters
}
```
- Defina uma nova classe de teste utilizando a anotação `@SpringBootTest`
- Realize a injeção do `JacksonTester` para o objeto `Aluno`
```java
JacksonTester<Aluno> json;
```
- Implemente o teste de serialização JSON para o objeto `Aluno`, conforme exemplo demonstrado (slides)
- Execute a classe de teste com sucesso
