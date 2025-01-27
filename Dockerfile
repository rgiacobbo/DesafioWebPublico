FROM openjdk:21-jdk-slim

RUN mkdir /app

WORKDIR /app

COPY target/*.jar /app/app.jar

RUN docker compose up -d

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
