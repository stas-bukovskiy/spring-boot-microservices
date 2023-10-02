# spring-boot-microservices
## Start on local machine
```bash
    gradle bootRun
```
Or just start via IntelliJ UI

## Start as docker containers
```bash
  docker compose -f docker-compose-prod.yml up -d
```

## Start with kubernetes
### Requirements
- docker
- minikube
### Usage
```bash
cd path/to/root/spring-boot-microservices
minikube start
kubectl apply -f kube/
minikube service --all

```