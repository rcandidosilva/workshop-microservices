# Laboratório 10

## Objetivos
- Explorando recursos adicionais no Spring Boot

## Tarefas
### Defina um batch job para importar dados de alunos de um arquivo CSV
- Utilize o projeto do exercício anterior
- Configure a dependência do projeto Spring Batch
```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-batch</artifactId>
  </dependency>
```
- Habilite o processo batch na aplicação Spring Boot utilizando `@EnableBatchProcessing`
- Implemente uma classe `Reader`, `Processor` e `Writer` para importar dados de alunos de um arquivo CSV
  - DICA: Utilize como referência para implementação, o seguinte exemplo:
    - http://www.javainuse.com/spring/bootbatch
```
nome,matricula,email,data nascimento
Rodrigo,111111,rodrigo@email.com,29/01/1982
Joao,222222,joao@email.com,03/05/1976
Maria,333333,maria@email.com,04/06/1985
Jose,444444,jose@email.com,08/01/1988
```
- Configure os beans de `step` e o `job` na aplicação para o processo de importação
- Execute e teste a aplicação

### Ative o mecanismo de cache na aplicação
- Utilize o projeto definido anteriormente
- Configure a dependência do projeto Spring Cache
```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-cache</artifactId>
  </dependency>
```
- Habilite o sistema de cache utilizando a anotação `@EnableCaching`
- Utilize cache em alguma consulta da aplicação com `@Cacheable`
- Execute e teste a aplicação

### Implemente um WebSocket para um chat entre alunos
- Utilize o projeto defindo anteriormente
- Configure a dependência do projeto Spring WebSocket
```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-websocket</artifactId>
  </dependency>
```
- Habilite e configure o WebSocket broker na aplicação
```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").withSockJS();
    }
}
```
- Implemente um controller endpoint para recebimento e envio das mensagens de chat
```java
@MessageMapping("/chat")
@SendTo("/topic/messages")
public OutputMessage send(Message message) throws Exception {
    String time = new SimpleDateFormat("HH:mm").format(new Date());
    return new OutputMessage(message.getFrom(), message.getText(), time);
}
```
- Implemente uma aplicação WebSocket cliente para chat entre alunos
  - DICA: Utilize como referência para implementação o seguinte exemplo:
    - http://www.baeldung.com/websockets-spring
- Execute e teste a aplicação 
