# Laboratório 4

## Objetivos
- Desenvolvendo Web com Spring MVC

## Tarefas
### Implemente um CRUD Web de Alunos
- Crie um novo projeto Spring Boot
- Implemente um classe para representar o objeto `Aluno`
```java
class Aluno {
  String nome;
  Integer matricula;
  String email;
  // getters/setters
}
```
- Implemente um `@Controller` para controlar as telas do CRUD de Alunos com as ações de `listar`, `criar`, `atualizar` e `excluir`
- Implemente um `index.html` com a lista de alunos e adicione no diretório `/templates`
- Inicialize uma lista no controller e compartilhe com a tela de listagem
- Implemente um `aluno.html` com o formulário de cadastro de aluno e adicione no diretório `/templates`
- Implemente as ações de `salvar` e `cancelar` para criação e/ou edição de alunos, vinculado-as com o controller respectivo
- Implemente a ação de `excluir` um aluno e recarrege a tela de listagem
- Execute e teste a aplicação

### Defina telas customizadas para HTTP errors
- Utilize o projeto definido anteriormente
- Defina uma tela customizada para o erro HTTP 404
- Defina uma tela customizada para o erro HTTP 500
- Execute e teste a aplicação, simulando os erros customizados

### Defina um tratamento de exceções via @ExceptionHandler
- Utilize o projeto defindo anteriormente
- Implemente um método para tratamento de exceção utilizando `@ExceptionHandler`
- Execute a aplicação, simulando a exceção sendo lançada e verificando o tratamento executado

### Implemente uma classe de teste para @Controller
- Utilize o projeto defindo anteriormente
- Implemente uma classe de teste para o controller definido para CRUD de Alunos
- Utilize o objeto `MockMvc` para ...
