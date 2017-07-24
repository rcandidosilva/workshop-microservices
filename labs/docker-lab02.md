# Laboratório 02

## Objetivos
- Publique as imagens Docker dos microservices no Nexus

## Tarefas

### Realize a instalação do Nexus
- Vamos realizar a instalação do Nexus via Docker
- Basta utilizar uma imagem oficial do Nexus 3 via Docker Hub
  - https://hub.docker.com/r/sonatype/nexus3/
```
docker run -d -p 8081:8081 -p 18081:18081 --name nexus sonatype/nexus3
```
- Teste a aplicação em execução
  - http://localhost:8081
  - Para acessar a interface administrativa pode utilizar as credenciais (admin / admin123)
  - Será necessário realizar a criação de um novo repositório privado Docker `microservices` no ambiente do Nexus

### Publique as imagens Docker no repositório Nexus
- Utilize as imagens Docker dos projetos de microservices produzidas anteriormente
- Realize o Docker login no repositório Nexus
```
docker login -u admin -p admin123 localhost:18081
```
- Defina tags para as imagens Docker produzidas anteriormente
```
docker tag microservices/config-server localhost:18081/microservices/config-server
docker tag microservices/eureka-server localhost:18081/microservices/eureka-server
docker tag microservices/security-service localhost:18081/microservices/security-service
docker tag microservices/hystrix-dashboard localhost:18081/microservices/hystrix-dashboard
docker tag microservices/aluno-service localhost:18081/microservices/aluno-service
docker tag microservices/disciplina-service localhost:18081/microservices/disciplina-service
docker tag microservices/zuul-server localhost:18081/microservices/zuul-server
```
- Publique as images Docker no repositório Nexus
```
docker push localhost:18081/microservices/config-server
docker push localhost:18081/microservices/eureka-server
docker push localhost:18081/microservices/security-service
docker push localhost:18081/microservices/hystrix-dashboard
docker push localhost:18081/microservices/aluno-service
docker push localhost:18081/microservices/disciplina-service
docker push localhost:18081/microservices/zuul-server
```
- Verifique as imagens publicas no repositório Nexus na interface Web administrativa
  - http://localhost:8081
