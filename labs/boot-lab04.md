# Laboratório 4

## Objetivos
- Desenvolvendo Web com Spring MVC

## Tarefas
### Implemente um CRUD Web de Alunos
- Crie um novo projeto Spring Boot
- Implemente um classe para representar o objeto `Aluno`
```java
class Aluno {
  Long id;
  String nome;
  Integer matricula;
  String email;
  // getters/setters
}
```
- Implemente um `@Controller` para controlar as telas do CRUD de Alunos com as ações de `listar`, `criar`, `atualizar` e `excluir`
- Implemente um `index.html` com a lista de alunos e adicione no diretório `/templates`
- Inicialize uma lista no controller e compartilhe com a tela de listagem
- Implemente um `aluno.html` com o formulário de cadastro do aluno e adicione no diretório `/templates`
- Implemente as ações de `salvar` e `cancelar` para criação e/ou edição do aluno, vinculado-as com as ações no controller respectivo
- Implemente a ação de `excluir` um aluno e recarrege a tela de listagem
- Execute e teste a aplicação

### Defina telas customizadas para HTTP errors
- Utilize o projeto definido anteriormente
- Defina uma tela customizada para o erro HTTP 404
- Defina uma tela customizada para o erro HTTP 401
- Defina uma tela customizada para o erro HTTP 500
- Execute e teste a aplicação, simulando os erros customizados

### Defina um tratamento de exceções via @ExceptionHandler
- Utilize o projeto defindo anteriormente
- Implemente um método para tratamento de exceção no controller utilizando a configuração `@ExceptionHandler`
- Simule a exceção sendo disparada por meio de alguma ação dentro do controller
- Execute a aplicação, e verifique o tratamento sendo executado

### Implemente uma classe de teste para @Controller
- Utilize o projeto defindo anteriormente
- Implemente uma classe de teste para o controller de alunos
  - Utilize a configuração `@WebMvcTest` para facilitar a implementação do unit test
- Injete o objeto `MockMvc` para simular as requisições HTTP send realizadas no controller
- Execute a classe de teste com sucesso
