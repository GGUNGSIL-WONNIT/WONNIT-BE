FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/snut-likelion-app.jar /app/snut-likelion-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app/snut-likelion-app.jar"]