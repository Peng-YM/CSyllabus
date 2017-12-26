# CSyllabus
## Deployment Guide

### 1. Create Database
Create Database using the provided script(Path: "src/main/resources/static/db/schema.sql")
```
mysql -u [UserName] -p [DatabaseName] < schema.sql
```

Note that you should modify the database configuration in the configuration file `application.yml`(Path: "src/main/resources/application.yml")
```
spring:
  datasource:
    url: jdbc:mysql://localhost:[Port]/[DatabaseName]
    username: [UserName]
    password: [Password]
```

### 2. Run Server
### 2.1 Running from IDE

You can use **Eclipse** or **IDEA**, import the project as `Maven` project then click "run as Java Application".

### 2.2 Using the Maven plugin (Recommended)
First you should install Maven according to your platform. The Spring Boot Maven plugin includes a run goal which can be used to quickly compile and run your application. Applications run in an exploded form just like in your IDE.
```
$ mvn spring-boot:run
```
You might also want to use the useful operating system environment variable:

```
$ export MAVEN_OPTS=-Xmx1024m -XX:MaxPermSize=128M
```

### 2.3 Running as a packaged application(Production Environment)
If you use the Spring Boot Maven or Gradle plugins to create an executable jar you can run your application using java -jar. For example:
1. create jar package
```
$ mvn package
```
2. run jar package
```
$ java -jar target/CSyllabus-0.0.1-SNAPSHOT.jar
```

### 3. Run Client
#### 3.1 Install Dependencies
1. [Install Node.js](https://nodejs.org/en/download/package-manager/)
2. Install Angular
```
$ cd Front-End-App
$ npm install -g @angular/cli
```
3. Install other package
```
$ npm install ng2-pdf-viewer --save
$ npm install ngx-cookie-service --save
```
#### 3.2 Run client
```
$ ng serve --open
```
