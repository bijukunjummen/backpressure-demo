# A project demonstrating Project Reactor backpressure behavior

## Steps to run the application 

First create the image for the app using(This uses the [Jib gradle plugin](https://github.com/GoogleContainerTools/jib)): 

```bash
./gradlew jibDockerBuild
```

and then

```bash
docker-compose up
```

Graphana at http://<dockerip>:3000


## Trigger a Producer and Consumer

### Scenario 1: Producer and Consumer in sync.

```
http "http://localhost:8080/scenario1?producerRate=100&consumerRate=3&count=100"
# OR 
curl "http://localhost:8080/scenario1?producerRate=100&consumerRate=3&count=100"
```

### Scenario 2: Producer and Consumer in different threads
(with a default prefetch of 256)
```
http "http://localhost:8080/scenario2?producerRate=100&consumerRate=3&count=300"
# OR
curl "http://localhost:8080/scenario2?producerRate=100&consumerRate=3&count=300"
```

(Smaller prefetch)
```
http "http://localhost:8080/scenario2?producerRate=100&consumerRate=3&count=300&prefetch=10"
# OR
curl "http://localhost:8080/scenario2?producerRate=100&consumerRate=3&count=300&prefetch=10"
```

### Scenario 3: Consumer consuming in parallel

```
http "http://localhost:8080/scenario3?producerRate=10&consumerRate=1&count=300&prefetch=10&concurrency=5"
# OR
curl "http://localhost:8080/scenario3?producerRate=10&consumerRate=1&count=300&prefetch=10&concurrency=5"
```
