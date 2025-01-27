FROM openjdk:21-jdk-slim

RUN mkdir /app

WORKDIR /app

COPY target/WebPublico-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
