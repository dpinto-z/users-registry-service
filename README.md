# Users Registry Service

This service allow to register, Users and search for all the users in thse system, also.

## Tech stack

* REST Full services.
* Framework Spring Boot 3.5.5
* Java 17
* Gradle as build tool.
* java JPA
* H2 in memory database. 
* Unit tests (JUnit).

# How to compile

You should use the following command on the terminal.
```
./gradlew clean build
```

## How to run

You should use the following command on the terminal.
```
./gradlew bootRun
```

## Documentation of REST api in

you should go the following path to access the service and the swagger page at

```
http://localhost:8080/user-registry-service/swagger-ui.html
```
## REST api Endpoints
The /user-registry and the /refresh-token endpoints are not secure by jwt token
http://localhost:8080/user-registry-service/user-management/user-registry
http://localhost:8080/user-registry-service/user-management/refresh-token

The /all-users /update-user and /delete-user are secure by the jwt token
and the token has to be passed as Authorization in the request
http://localhost:8080/user-registry-service/user-management/all-users
http://localhost:8080/user-registry-service/user-management/update-user
http://localhost:8080/user-registry-service/user-management/delete-user




Since this project use H2 in memory database, 
you should use the following path to access to the database console.
```
http://localhost:8080/user-registry-service/h2-console/
```
And use as JDBC URL the password is not needed
```
jdbc:h2:mem:registry-db
```

## How to test

Use the following command on the terminal will run all tests
```
./gradlew test
```
