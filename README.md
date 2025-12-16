# MS etudiant-backend

Backend service managing user authentication APIs and Student CRUD operations for the application.

## ⚙️ Backend Configuration

| Name | Value |
| :--- | :--- |
| **Service Name** | `etudiant-backend` |
| **Port** | `8080` |

## ✅ Prerequisites

For the service to run correctly, you must have the following installed:

* **JDK**: Version 21 or higher.
* **Docker**
* **Docker Compose**
* **Maven**: Version 3.9.3 or higher (https://archive.apache.org/dist/maven/maven-3/3.9.3/binaries/)

## 🚀 Starting the Backend

To start the backend project, perform the following steps:

1.  Ensure **Docker Desktop** is running on your local machine.
2.  In a console, navigate to the project root directory.
3.  Execute the following Maven command:

```
mvn spring-boot:run
```

This command will:
* Initialize the Docker container hosting the MySQL database.
* Launch the backend server and connect it to the previously created database.

The log traces should look similar to this:
```
.   ____          _            __ _ _
/\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
\\/  ___)| |_)| | | | | || (_| |  ) ) ) )
'  |____| .__|_| |_|_| |_\__, | / / / /
=========|_|==============|___/=/_/_/_/

:: Spring Boot ::                (v3.5.5)

[etudiant-backend] [           main] c.o.etudiant.EtudiantBackendApplication  : Starting EtudiantBackendApplication using Java 21.0.3 with PID 6964
[etudiant-backend] [           main] c.o.etudiant.EtudiantBackendApplication  : No active profile set, falling back to 1 default profile: "default"
[etudiant-backend] [           main] .s.b.d.c.l.DockerComposeLifecycleManager : Using Docker Compose file ******etudiant-backend\compose.yaml*****
[etudiant-backend] [utReader-stderr] o.s.boot.docker.compose.core.DockerCli   :  Container etudiant-backend-mysql-1  Created
[etudiant-backend] [utReader-stderr] o.s.boot.docker.compose.core.DockerCli   :  Container etudiant-backend-mysql-1  Starting
[etudiant-backend] [utReader-stderr] o.s.boot.docker.compose.core.DockerCli   :  Container etudiant-backend-mysql-1  Started
[etudiant-backend] [utReader-stderr] o.s.boot.docker.compose.core.DockerCli   :  Container etudiant-backend-mysql-1  Waiting
[etudiant-backend] [utReader-stderr] o.s.boot.docker.compose.core.DockerCli   :  Container etudiant-backend-mysql-1  Healthy
[etudiant-backend] [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
[etudiant-backend] [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 39 ms. Found 1 JPA repository interface.
[etudiant-backend] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
[etudiant-backend] [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
[etudiant-backend] [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.44]
[etudiant-backend] [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
[etudiant-backend] [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1354 ms
[etudiant-backend] [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
[etudiant-backend] [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.6.26.Final
[etudiant-backend] [           main] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
[etudiant-backend] [           main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
[etudiant-backend] [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
[etudiant-backend] [           main] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection com.mysql.cj.jdbc.ConnectionImpl4db16677
[etudiant-backend] [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
[etudiant-backend] [           main] org.hibernate.orm.connections.pooling    : HHH10001005: Database info:
[etudiant-backend] [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
[etudiant-backend] [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
[etudiant-backend] [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
[etudiant-backend] [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 1 endpoint beneath base path '/actuator'
[etudiant-backend] [           main] eAuthenticationProviderManagerConfigurer : Global AuthenticationManager configured with AuthenticationProvider bean with name authenticationProvider
[etudiant-backend] [           main] r$InitializeUserDetailsManagerConfigurer : Global AuthenticationManager configured with an AuthenticationProvider bean. UserDetailsService beans w
ill not be used by Spring Security for automatically configuring username/password login. Consider removing the AuthenticationProvider bean. Alternatively, consider using the UserDetailsService in a manually instantiated DaoAuth
enticationProvider. If the current configuration is intentional, to turn off this warning, increase the logging level of 'org.springframework.security.config.annotation.authentication.configuration.InitializeUserDetailsBeanManagerConfigurer' to ERROR
[etudiant-backend] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
[etudiant-backend] [           main] c.o.etudiant.EtudiantBackendApplication  : Started EtudiantBackendApplication in 10.27 seconds (process running for 10.642)
```

You should see a MySQL container appear in Docker Desktop corresponding to the project.

#### Database Access Details

You can connect to the database to verify the automatic creation of the `user` table. Click on the `mysql-1` link in Docker Desktop to reach the full database view.

In the `Exec` tab:

1.  **Connect to the database.** Type the command below:

    ```
    mysql -u etudiant_db -p
    ```
    The command prompt will ask for the password. It is identical to the username: `etudiant_db`.

2.  **Connect to the `etudiant_db` schema.** In the command prompt, type the command below:

    ```
    use etudiant_db;
    ```
  
3.  **Verify the existence of the `user` table** (it is currently empty).

    ```
    select * from user;
    ```
    The expected result should be: `Empty set (0.00 sec)`

The screenshot below summarizes the previous steps:

![2-docker-desktop-bdd](pictures/2-docker-desktop-bdd.png)

## 🧪 Running Tests

To execute the JUnit tests, you must:

* Ensure **Docker Desktop** is running, as integration tests require Docker to create temporary testing databases.
* In a console, navigate to the project root directory.
* Execute the following Maven command:

```
mvn clean test
```

#### Detailed Test Coverage

The test suite includes Unit Tests (isolation) and Integration Tests (Dockerized database dependency).

* **Unit Tests (Services & Utility):**
    * **`UserService`**: Checks business logic related to user registration and retrieval.
    * **`StudentService`**: Checks business logic related to student CRUD operations.
    * **`JwtService`**: Validates token generation, validation, and extraction of claims (unit testing the JWT functionality).
    * **Entity Classes (User/Student)**: Simple verification of getters, setters, and constructors.

* **Integration Tests (Controllers & Repositories):**
    * **`/api/students` Resource**: Full integration tests covering the CRUD endpoints (`POST`, `GET`, `PUT`, `DELETE`) for students, verifying database interaction.
    * **`/api/register` and `/api/login` Resources**: Integration tests verifying user registration and authentication flow (including token return and security layer configuration).
    * **Repository Classes (User/Student)**: Integration tests verifying Spring Data JPA mapping and database queries against a test container.

## ✨ Supported Features

* API for creating a user (library agent).
* API for authenticating a user (**`/api/login`** - to be implemented).
* CRUD APIs for library students (to be implemented).