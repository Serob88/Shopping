# Shopping Application

This is simple Spring Boot application for demonstrating user authentication and e-commerce features.

## Get Started
In order to start application please setup following programs:

- JDK 8
- Maven 3
- PostgreSQL

For database setup create new schema in postgres database (e.g `shopping_application`).
Set up database credentials: `username`, `password`, `url` in `application.yml`
```yaml
spring:
  datasource:
    driverClassName: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: root
    hikari:
      schema: shopping_application
      connection-timeout: 5000
      maximum-pool-size: 10
```

To start application navigate to root folder and execute maven package command:
```
mvn clean package
mvn spring-boot:run
```

## Technology Stack
 - Java 8
 - Spring Boot
 - Spring MVC
 - Spring Security
 - Spring Data
 - Liquibase
 - Lombok
 - Swagger
 
 
> Notes*: by default an admin user will be created automaticly. Here is the username and password:

```
username: admin@shopping.com
passwrod: 123456 
```
