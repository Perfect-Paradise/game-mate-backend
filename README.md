# game-mate-backend

**Features**:

- Spring Boot 3
- Kotlin
- Gradle
- OAuth2

## Release Notes

### 2024/06/07

1. Imported Flyway db versioning tool
    - If you have existing db, please first drop all tables and run this application
    - On application startup, it will automatically pick the migration files (from `src/main/resources/db/migration`)
    - Flyway will create a table `flyway_schema_history` to record the execution of migration files
    - In the future, if you define new table, you should also provide the sql migration file to the above path
    - Additional note: it is convenient to use tool like "JPA Buddy" to generate sql from entity definition
2. Implement email/password signup and login APIs (and also updated to Postman collection)
    - There is a default account with email: `test@gmail.com` and password: `password` being inserted directly by the
      Flyway sql file.
    - You can register new account by `/auth/signup` API.
    - You can get jwt token by `/auth/login` API, and note that I have write a script that automatically set the
      response of this API to environment variable `jwt`. So, you don't need to manually copy/paste the token response

## Setup

- Clone this repo

```shell
git clone https://github.com/Perfect-Paradise/game-mate-backend
```

- JDK version: 17 or 21 (my personal choice: openjdk-21.0.2)

## Run

### Commend Line

```shell
./gradlew bootRun
```

### IntelliJ IDEA

![2024-05-23 12 02 03](https://github.com/Perfect-Paradise/game-mate-backend/assets/32578837/e5e82a99-9736-47da-a1b2-b6142682a62e)

### Docker

```shell
docker compose up
```

## Test

```shell
./gradlew test
```

## Postman Collection

### Import from file

![2024-05-23 12 17 09](https://github.com/Perfect-Paradise/game-mate-backend/assets/32578837/377dcf6a-e980-4f81-8274-ba3ee19035ec)

### Export to file

![2024-05-23 12 18 34](https://github.com/Perfect-Paradise/game-mate-backend/assets/32578837/468b7788-a04b-4f4a-ba87-9bb0e99b40f9)

### Environment variables

On local development

```
url: http://localhost:8080
socketUrl: ws://localhost:8081
jwt: {fetch from the response of login API}
```
