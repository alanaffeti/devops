FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY target/gestion-station-skii-0.0.1-SNAPSHOT.jar /app/gestion-station-skii.jar


ENTRYPOINT ["java", "-jar","/app.jar"]