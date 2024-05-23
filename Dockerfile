# First stage: build the application
FROM openjdk:17-jdk-slim as builder

# Set the working directory
WORKDIR /home/gradle/project

# copy gradle files
COPY gradle gradle
COPY gradlew .

# Copy the source code and the build file
COPY src src
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Build the project
RUN ./gradlew build -x test

# Use the official OpenJDK 17 image for the runtime
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file to the container
COPY --from=builder /home/gradle/project/build/libs/app.jar ./app.jar

# Expose the port the application runs on
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "app.jar"]