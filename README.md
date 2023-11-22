# spring-boot-microservices
## Start on local machine
1. Copy '.env.example' and rename it to '.env'
2. Start docker containers with docker-compose
```bash
    docker compose -f docker-compose-dev.yml up -d
```
3. Start config server
4. Start one or multiple services by gradle command or with IDE
```bash
    gradle bootRun
```


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