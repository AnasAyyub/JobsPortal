# Use an OpenJDK 21 base image
FROM eclipse-temurin:21-jdk-alpine

# Argument to specify the JAR file location
ARG JAR_FILE=target/*.jar

# Copy the application JAR file into the container
COPY ${JAR_FILE} app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]