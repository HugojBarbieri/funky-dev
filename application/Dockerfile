# Stage 1: Build
FROM maven:3.9-amazoncorretto-21 AS build

# Set the working directory
WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:21-jdk-slim

# Install necessary packages
RUN apt-get update && \
    apt-get install -y fontconfig fonts-dejavu && \
    apt-get clean

# Set the working directory
WORKDIR /app

# Copy the packaged JAR file from the build stage
COPY --from=build /app/target/application-0.0.1-SNAPSHOT.jar /app/application.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/application.jar"]
