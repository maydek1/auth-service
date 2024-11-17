FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/auth-service-0.0.1-SNAPSHOT.jar /app/auth-service.jar

ENTRYPOINT ["java", "-jar", "auth-service.jar"]