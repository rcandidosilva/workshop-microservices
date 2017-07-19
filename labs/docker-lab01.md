# Laboratório 01

## Objetivos
- Empacote os projetos de microservices com Docker

## Tarefas

### Instalação do ambiente Docker
- Realize a instalação do ambiente Docker no ambiente, caso ainda não tenha realizado
- Para instalação nativa
  - Windows
    - https://docs.docker.com/docker-for-windows/install/
  - Mac
    - https://docs.docker.com/docker-for-mac/install/
  - Ubuntu / Debian / Fedora
    - https://docs.docker.com/engine/installation/linux/docker-ce/ubuntu/
    - https://docs.docker.com/engine/installation/linux/docker-ce/debian/
    - https://docs.docker.com/engine/installation/linux/docker-ce/fedora/
- Para a instalação do Docker Toolbox
  - Windows
    - https://docs.docker.com/toolbox/toolbox_install_windows/
  - Mac
    - https://docs.docker.com/toolbox/toolbox_install_mac/

### Configure o Dockerfile para cada projeto de microservice
- Utilize os projetos de microservices criados anteriormente
- Defina um arquivo `Dockerfile` no diretório `src/main/docker` em cada projeto definido
```
FROM java:8

ARG SPRING_PROFILES_ACTIVE
ARG JAVA_OPTS
ARG PORT

ENV SPRING_PROFILES_ACTIVE ${SPRING_PROFILES_ACTIVE:-docker}
ENV JAVA_OPTS ${JAVA_OPTS:-'-Xmx512m'}
ENV DEBUG_OPTS ${DEBUG_OPTS}
ENV PORT ${PORT:-8080}

ADD *.jar /app.jar

VOLUME /tmp

RUN sh -c 'touch /app.jar'

EXPOSE ${PORT}

CMD java ${JAVA_OPTS} ${DEBUG_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar
```
- Será necessário definir um novo Spring Profile `docker` alterando o endereçamento de `localhost` para `172.17.0.1` (Docker default gateway)
- Configure o plugin `docker-maven-plugin` no `pom.xml` de cada projeto
```xml
<project>
  ...
  <build>
        <plugins>
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>0.4.13</version>
                <configuration>
                    <imageName>microservices/${project.artifactId}</imageName>
                    <dockerDirectory>${project.basedir}/src/main/docker</dockerDirectory>
                    <resources>
                        <resource>
                            <targetPath>/</targetPath>
                            <directory>${project.build.directory}</directory>
                            <include>${project.build.finalName}.jar</include>
                        </resource>
                    </resources>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```
- Realize o build da aplicação e verifique as Docker images sendo criada para cada microservice
  - `mvn clean package docker:build`

### Execute a aplicação utilizando as images Docker definidas
- Liste e identifique todas as imagens definidas no registro Docker local
```
docker images
```
- Execute cada imagem Docker de cada microservice definido
```
docker run -d -p 8888:8888 --name config microservices/config-server
docker run -d -p 8761:8761 --name eureka microservices/eureka-server
docker run -d -p 9999:9999 --name security microservices/security-service
docker run -d -p 7979:7979 --name hystrix microservices/hystrix-dashboard
docker run -d -p 8080:8080 --name aluno microservices/aluno-service
docker run -d -p 8081:8081 --name disciplina microservices/disciplina-service
docker run -d -p 8000:8000 --name zuul microservices/zuul-server
```
- Verifique todas as imagens rodando no ambiente local
```
docker ps
```
- Execute e teste a aplicação
- Termine a execução de cada imagem Docker iniciada anteriormente
```
docker stop config
docker stop eureka
docker stop security
docker stop hystrix
docker stop aluno
docker stop disciplina
docker stop zuul
```
