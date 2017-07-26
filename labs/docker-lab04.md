# Laboratório 04

## Objetivos
- Realize a instalação do Kubernetes cluster na sua máquina local

## Tarefas

### Realize a instalação do Minikube Kubernetes cluster
- Faça o download e instale o VirtualBox, caso ainda não o tenha instalado
  - https://www.virtualbox.org/wiki/Downloads
- Faça o download e instale o Vagrant, caso ainda não o tenha instalado
  - https://www.vagrantup.com/downloads.html  
- Faça a instalação do Minikube
  - https://github.com/kubernetes/minikube
- Faça a instalação do Kubectl CLI
  - https://kubernetes.io/docs/tasks/tools/install-kubectl/  
- Inicie o Minikube Kubernetes cluster
```
minikube start
```
- Acesse o Minikube Dashboard
```
minikube dashboard
```

### Execute um primeiro container no Kubernetes cluster
- Crie e inicie um primeiro deployment no cluster
```
kubectl run hello-wildfly --image=jboss/wildfly:10.1.0.Final --port=8080
```
- Verifique se o deployment realizado encontra-se em execução
```
kubectl get deployments
```
- Você pode também verificar mais informações sobre este deployment
```
kubectl describe deployments hello-wildfly
```
- Para acessar esse deploymente externamente ao cluster, será necessário expor a porta de execução
```
kubectl expose deployment hello-wildfly --name=hello-wildfly-service --port=8080 --target-port=8080
```
- Tente acessar agora o ambiente do Wildfly externamente ao cluster. Para acessar será necessário descobrir o IP do cluster
```
minikube ip
```
- Verifique os componentes definidos no Kubernetes cluster por meio do Minikube Dashboard
```
minikube dashboard
```
- Por enfim, remova o deployment realizado anteriormente
```
kubectl delete deployment/hello-wildfly
```
