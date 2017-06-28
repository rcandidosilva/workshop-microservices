# Laboratório 04

## Objetivos
- Ativando o suporte à replicação com Netflix Eureka

## Tarefas

### Configure replicação no Eureka Server
- Utilize os projetos definidos no exercício anterior
- Adicione as configurações de suporte à replicação para réplica `peer1 ` e réplica `peer2` na configuração do Eureka Server
```
...
---
spring:
  profiles: peer1

server:
  port: 8762

eureka:
  instance:
    hostname: peer1
  client:
    serviceUrl:
      defaultZone: http://peer2:8763/eureka/  

---
spring:
  profiles: peer2

server:
  port: 8763

eureka:
  instance:
    hostname: peer2
  client:
    serviceUrl:
      defaultZone: http://peer1:8762/eureka/  
```
- Configure os hostnames `peer1` e `peer2` no registro DNS do sistema operacional
```
127.0.0.1       peer1
127.0.0.1       peer2
```
- Realize essa configuração de acordo com cada sistema operacional
  - Windows
    - `C:\windows\system32\drivers\etc\hosts`
  - Linux
    - `/etc/hosts`
  - Mac OS
    - `/etc/hosts`
- Execute as duas réplicas Eureka e verifique a conexão sendo realizada via console
  - Não se esqueça de rodar cada réplica com seu profile específico definido (`peer1`, `peer2`)
    - `mvn spring-boot:run -Dspring.profiles.active=[profile]`

### Registre o(s) Eureka Client(s) em uma instância diferente do Eureka Server
- Utilize os projetos definidos anteriormente
- Analise a configuração definida pelos projetos para conexão com o Eureka Server
```
spring:
  application:
    name: [service]

eureka:
  client:
    serviceUrl:
      defaultZone: ${EUREKA_URI:http://localhost:8761/eureka}
  instance:
    preferIpAddress: true
```
- Execute a aplicação utilizando a variável de ambiente `EUREKA_URI` para informar a réplica do Eureka Server à ser registrado
  - `spring-boot:run -DEUREKA_URI=http://peer1:8762/eureka`
  - `spring-boot:run -DEUREKA_URI=http://peer2:8763/eureka`
- Verifique se a aplicação foi registrada corretamente na réplica definida e se a mesma foi replicada para a outra instância do cluster Eureka definido
