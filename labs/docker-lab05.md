# Laboratório 05

## Objetivos
- Orquestre o deployment da arquitetura de microservices com Kubernetes

## Tarefas

## Faça o build e envie as imagens Docker para o Kubernetes cluster
- Para enviar as imagens Docker dos microservices será necessário definir um Docker Registry no ambiente do Kubernetes cluster
- Para realizar a instalação / ativação deste registry, execute o seguinte comando
```
kubectl apply --filename https://raw.githubusercontent.com/giantswarm/kubernetes-registry/master/manifests-all.yaml
```
- Identifique o IP do Kubernetes cluster em execução
```
minikube ip
```
- Defina tags para as imagens Docker dos projetos de microservices
```
docker tag microservices/config-server minikube-ip:5000/microservices/config-server
docker tag microservices/eureka-server minikube-ip:5000/microservices/eureka-server
docker tag microservices/security-service minikube-ip:5000/microservices/security-service
docker tag microservices/hystrix-dashboard minikube-ip:5000/microservices/hystrix-dashboard
docker tag microservices/aluno-service minikube-ip:5000/microservices/aluno-service
docker tag microservices/disciplina-service minikube-ip:5000/microservices/disciplina-service
docker tag microservices/zuul-server minikube-ip:5000/microservices/zuul-server
```
- Publique as images Docker no registry do Kubernetes cluster
```
docker push minikube-ip:5000/microservices/config-server
docker push minikube-ip:5000/microservices/eureka-server
docker push minikube-ip:5000/microservices/security-service
docker push minikube-ip:5000/microservices/hystrix-dashboard
docker push minikube-ip:5000/microservices/aluno-service
docker push minikube-ip:5000/microservices/disciplina-service
docker push minikube-ip:5000/microservices/zuul-server
```

### Realize o deployment de cada microservice no ambiente do Kubernetes cluster
- Faça o deployment de cada microservice no ambiente do cluster
```
kubectl run config-server --image=localhost:5000/microservices/config-server --port=8888 --expose=true
kubectl run eureka-server --image=localhost:5000/microservices/eureka-server --port=8761 --expose=true
kubectl run security-server --image=localhost:5000/microservices/security-server --port=9999 --expose=true
kubectl run hystrix-dashboard --image=localhost:5000/microservices/hystrix-dashboard --port=7979 --expose=true
kubectl run aluno-service --image=localhost:5000/microservices/aluno-service --port=8080 --expose=true
kubectl run disciplina-service --image=localhost:5000/microservices/disciplina-service --port=8081 --expose=true
kubectl run zuul-server --image=localhost:5000/microservices/zuul-server --port=8000 --expose=true
```
- Teste a aplicação de microservices rodando no ambiente do Kubernetes cluster
