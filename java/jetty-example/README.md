## test

compile and run
```shell
mvn clean package -Dmaven.test.skip=true
java -jar target/jetty-example-1.0-SNAPSHOT.jar
```

POST
```shell
curl -X POST -H 'Content-Type: application/json' -d '{"name": "Peter Zuo"}'  localhost:8080/test/SaveName
```

GET
```shell
curl -X GET localhost:8080/test/GetName
```
