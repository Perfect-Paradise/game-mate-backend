# First stage: build the application
FROM eclipse-temurin:21 as builder

# Set the working directory
WORKDIR /home/gradle/project

# copy gradle files
COPY gradle gradle
COPY gradlew .

# Copy the build files and download dependencies
COPY build.gradle.kts .
COPY settings.gradle.kts .
RUN ./gradlew dependencies

# Copy the source code
COPY src src

# Build the project
RUN ./gradlew build -x test

# Use a smaller image for the final stage
FROM nginx:stable-alpine

# Update package lists and install java
RUN apk add openjdk21-jre

# Set the working directory
WORKDIR /app

# Copy the JAR file to the container
COPY --from=builder /home/gradle/project/build/libs/app.jar ./app.jar

# Copy the Nginx configuration file
COPY nginx.conf /etc/nginx/nginx.conf.template

ENV PORT=10000
ENV SPRING_PORT=8080
ENV SOCKET_PORT=8081

# Expose the port
EXPOSE ${PORT}

# Entry point script that do envsubst by environment variables on startup
COPY docker-entrypoint.sh /
RUN chmod +x /docker-entrypoint.sh

ENTRYPOINT ["/docker-entrypoint.sh"]

# Start Nginx and the application
CMD nginx && java -jar app.jar