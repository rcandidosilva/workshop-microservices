# Laboratório 03

## Objetivos
- Componha a arquitetura de microservices utilizando Docker Compose

## Tarefas

### Configure a composição com Docker Compose
- Crie um arquivo `docker-compose.yml` para configurar a composição
```yml
version: '2'
services:
  ...  
networks:
  microservices:
    driver: bridge
```
- Configure o serviço do `config-server` na composição
```yml
config-server:
  image: microservices/config-server:latest
  mem_limit: 1073741824 # RAM 1GB
  environment:
    - SPRING_PROFILES_ACTIVE=docker
  expose:
    - "8888"
  ports:
    - "8888:8888"
  networks:
    - microservices
```
- Configure o serviço do `eureka-server` na composição
```yml
eureka-server:
  image: microservices/eureka-server:latest
  mem_limit: 1073741824 # RAM 1GB
  environment:
    - SPRING_PROFILES_ACTIVE=docker
  depends_on:
    - config-server
  expose:
    - "8761"
  ports:
    - "8761:8761"
  networks:
    - microservices
```
- Configure o serviço do `security-server` na composição
```yml
security-server:
  image: microservices/security-server:latest
  mem_limit: 1073741824 # RAM 1GB
  environment:
    - SPRING_PROFILES_ACTIVE=docker
  depends_on:
    - config-server
    - eureka-server
  expose:
    - "9999"
  ports:
    - "9999:9999"
  networks:
    - microservices
```
- Configure o serviço do `aluno-service` na composição
```yml
aluno-service:
  image: microservices/aluno-service:latest
  mem_limit: 1073741824 # RAM 1GB
  environment:
    - SPRING_PROFILES_ACTIVE=docker
  links:
    - config-server
    - eureka-server
    - security-server
  expose:
    - "8080"
  ports:
    - "8080:8080"
  networks:
    - microservices
```
- Configure o serviço da `disciplina-service` na composição
```yml
disciplina-service:
  image: microservices/disciplina-service:latest
  mem_limit: 1073741824 # RAM 1GB
  environment:
    - SPRING_PROFILES_ACTIVE=docker
  depends_on:
    - config-server
    - eureka-server
    - security-server
  expose:
    - "8081"
  ports:
    - "8081:8081"
  networks:
    - microservices
```
- Configure o serviço do `zuul-server` na composição
```yml
zuul-server:
  image: microservices/zuul-server:latest
  mem_limit: 1073741824 # RAM 1GB
  environment:
    - SPRING_PROFILES_ACTIVE=docker
  depends_on:
    - config-server
    - eureka-server
    - security-server
    - aluno-service
    - disciplina-service
  expose:
    - "8000"
  ports:
    - "8000:8000"
  networks:
    - microservices
```
- Modifique as configurações de conexão com o Config Server no arquivo `bootstrap.yml` dos serviços
```yml
---
spring:
  profiles: docker
  cloud:
    config:
      uri: http://config-server:8888
      username: configUser
      password: configPassword      
```
- Será necessário reconfigurar a url de conexão com o Eureka nas propriedades dos serviços
```yml
eureka:
 client:
   serviceUrl:
     defaultZone: ${EUREKA_URI:http://eurekaUser:eurekaPassword@eureka-server:8761/eureka}
 instance:
   preferIpAddress: true
```
- É necessário também reconfigurar a url de conexão com o Security nas propriedades dos serviços
```yml
security:
  oauth2:
    resource:
      userInfoUri: http://security-server:9999/users/current  
```

### Rode a composição definida com Docker Compose
- Utilize a composição definida anteriormente no arquivo `docker-compose.yml`
- Será necessário utilizar o utilitário `docker-compose` para rodar a composição
- Realize a instalação do mesmo, caso ainda não o tenha realizado
  - https://docs.docker.com/compose/install/
- Execute e teste a composição
```
docker-compose up
docker-compose down
```
- Foi possível executar corretamente a composição? Os serviços subiram corretamente?
- Será necessário remodelar as images Docker para suportar a dependência entre serviços
  - Pode ser utilizado o suporte do projeto `Dockerize` para definir essas dependências
    - https://github.com/jwilder/dockerize
- Execute e teste novamente a composição
```
docker-compose up
docker-compose down
```
