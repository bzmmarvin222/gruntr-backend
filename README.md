[![Build Status](https://travis-ci.org/bzmmarvin222/gruntr-backend.svg?branch=master)](https://travis-ci.org/bzmmarvin222/gruntr-backend)

# Gruntr Backend
This is an example Spring Boot Backend. See [Gruntr](https://github.com/bzmmarvin222/gruntr) to learn more about Gruntr in general.

### Documentation/ Introduction into used Frameworks
- [Java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Gradle](https://gradle.org/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [REST + HATEOAS](https://spring.io/understanding/HATEOAS)
- [JPA with Hibernate](https://spring.io/guides/gs/accessing-data-jpa/)
- [Swagger/ Swagger UI](http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api)
- [JUnit](https://junit.org/junit5/)
- [Mockito](http://site.mockito.org/)

## After Cloning
1. import the project as a gradle project in your IDE
2. setup a Spring Boot run configuration with main class `de.brockhausag.gruntr.GruntrApplication`
3. run and visit [localhost:8080/login](http://localhost:8080/login)

## During Development
Initially, there are always 2 users. One is a default user, the other one is an admin.
The credentials are:
##### admin; admin
##### user; user

### Swagger UI
To see which Controllers are available and to test them, visit Swagger UI.
Just go to [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)