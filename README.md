## Requirements

- java 21
- podman

## Basic commands

```bash
./gradlew help
```

```bash
./gradlew tasks
```

```bash
./gradlew bootRun
```

```bash
./gradlew bootBuildImage
```

```bash
docker images catalog-service:0.0.1-SNAPSHOT
```

```bash
docker run --rm --name catalog-service -p 8080:8080 \
    catalog-service:0.0.1-SNAPSHOT
```
[see localhost:8080](http://localhost:8080/)

## Local Kubernetes Deployment

```bash
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-latest.x86_64.rpm
```
```bash
sudo rpm -Uvh minikube-latest.x86_64.rpm
```
```bash
minikube config set driver podman
```
```bash
minikube config set rootless true
```
```bash
minikube start
```
```bash
minikube image load catalog-service:0.0.1-SNAPSHOT
```
```bash
kubectl create deployment catalog-service \
    --image=catalog-service:0.0.1-SNAPSHOT
```
```bash
kubectl logs deployment/catalog-service
```
```bash
kubectl expose deployment catalog-service \
    --name=catalog-service \
    --port=8080
```
```bash
kubectl port-forward service/catalog-service 8000:8080
```
[see localhost:8000](http://localhost:8000/)

```bash
## kubectl get service
kubectl delete service catalog-service

## kubectl get deployment
kubectl delete deployment catalog-service
```

## Architecture of Polar Bookshop

![Architecture of Polar Bookshop](./images/fig-2.14-architecture-Polar_Bookshop.png "qwe")

```bash
./gradlew bootJar
```
```bash
java -jar build/libs/catalog-service-0.0.1-SNAPSHOT.jar
```
```bash
# NOTE Another practical Gradle task is build, 
# which combines the operations of the 
# bootJar and test tasks.
./gradlew build
```
```bash
./gradlew bootRun
```
[see localhost:9001](http://localhost:9001/)

```bash
http :9001/
```
```bash
java -jar build/libs/catalog-service-0.0.1-SNAPSHOT.jar \
    --spring.profiles.active=prod
```
```bash
http POST :9001/actuator/refresh
```
```bash

```

## Chapter 5

```bash
cd ../config-service
./gradlew bootRun
```
Remember to run the config-service before trying the catalog-service.

```bash
docker run -d \
    --name polar-postgres \
    -e POSTGRES_USER=user \
    -e POSTGRES_PASSWORD=password \
    -e POSTGRES_DB=polardb_catalog \
    -p 5432:5432 \
    postgres:14.4
```

```bash
docker ps -a
```

```bash
docker stop polar-postgres
```

```bash
docker start polar-postgres
```

```bash
docker ps
```

```bash
docker rm -fv polar-postgres
```

```bash
./gradlew test
```

```bash
./gradlew bootRun
```

```
sudo dnf install httpie

http POST :9001/books author="Lyra Silverstar" \
    title="Northern Lights" isbn="1234567891" price=9.90

http POST :9001/books author="Syra Lilverstar" \
    title="Southern Lights" isbn="9234567891" price=999.90

http GET :9001/books

http :9001/books/1234567891

http :9001/books/9234567891
```

```bash
./gradlew test --tests BookRepositoryJdbcTests
./gradlew test --tests CatalogServiceApplicationTests
```
