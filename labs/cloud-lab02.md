# Laboratório 02

## Objetivos
- Replicando a configuração centralizada com Spring Cloud Config Bus

## Tarefas

### Ajuste a configuração do Config Server
- Utilize o projeto definido no exercício anterior
- Adicione a dependência `spring-cloud-config-monitor` e do `spring-cloud-starter-stream-rabbit` no projeto do config server
```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-monitor</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
    </dependency>
```
- Realize a instalação do `RabbitMQ` 
  - Windows
    - https://www.rabbitmq.com/install-windows.html
  - Linux
    - `apt-get install rabbitmq-server`
    - `yum install rabbitmq-server`
  - Mac OS
    - `brew install rabbitmq`
- Configure o suporte ao barramento no config server
```
server:
  port: 8888

management:
  security:
    enabled: false

spring:
  cloud:
    config:
      server:
        git:
          uri: file://local/git/config-repo        
    bus:
      enabled: true
  rabbitmq:
    host: localhost
    port: 5672          
```
- Execute e teste a aplicação

### Ajuste a configuração do Config Client
- Utilize o projeto definido no exercício anterior
- Adicione a dependência `spring-cloud-starter-bus-amqp` no seu projeto
```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-bus-amqp</artifactId>
    </dependency>
```
- Configure as seguintes propriedades no arquivo Boostrap
```
spring:
  application:
    name: cloud-lab01
  cloud:
    config:
      uri: http://localhost:8888
  rabbitmq:
    host: localhost
    port: 5672      
```
- Execute e teste a aplicação

### Modique propriedades e teste as mudanças replicadas via WebHook /monitor
- Utilize os projetos modificados anteriormente
- Execute os projetos config server e client, caso não estejam em execução
- Modifique o arquivo `cloud-lab01.yml` no repositório Git modificando as propriedades customizadas
- Não se esqueça de comitar o arquivo no repositório Git
```
$ git add .
$ git commit -m "Changes at cloud-lab01"
```
- Execute o REST endpoint imprimindo as propriedades configuradas
  - As propriedades foram atualizadas com sucesso?
- Simule um `WebHook Git push` para quando um arquivo é modificado no repositório Git
```
curl -v -X POST "http://localhost:8100/monitor" -H "Content-Type: application/json" -H "X-Event-Key: repo:push" -H "X-Hook-UUID: webhook-uuid" -d '{"push": {"changes": []} }'
```
- Execute novamente o REST endpoint e verifique se as propriedades modificadas
