
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