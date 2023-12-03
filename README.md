# spring-boot-microservices
This is a simple example of a microservice architecture with spring boot and docker. 
The project represents a simple discipline enrollment and managements system, where admins can manage faculties and 
theirs disciplines and students can enroll on disciplines. 
## Start locally with IDE
1. Create and write out`.env` as in example `.env.example` 
2. Start docker containers with docker-compose
```bash
    docker compose -f compose-local.yaml up -d
```
3. Generate proto models for grpc
```bash
    cd ./faculy
    gradle generateProto
    cd ../auth
    gradle generateProto
    cd ../enrollment
    gradle generateProto
    cd ../
```
3. Start config server
4. Start one or multiple services by gradle command or with IDE
```bash
    gradle bootRun
```

## Start as docker containers
1. Create and write out`.env` as in example `.env.example`
2. Build jar executables
```bash
    gradle bootJar
```
2. Execute docker compose command
```bash
  docker compose -f compose-prod.yaml up -d
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