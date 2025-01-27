FROM maven:3.8.6-openjdk-21-slim AS build

RUN mkdir /app

WORKDIR /app

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-alpine

WORKDIR /app

COPY target/*.jar /app/app.jar

RUN docker compose up -d

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
