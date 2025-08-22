FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/wonnit-app.jar /app/wonnit-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app/wonnit-app.jar"]