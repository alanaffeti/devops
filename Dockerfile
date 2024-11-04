FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
COPY target/gestion-station-skii-1.0.jar app.jar
EXPOSE 8080
ENV SPRINGPROFILES=prod
ENTRYPOINT ["java", "-jar", "app.jar","-Dspring.profiles.active=${SPRINGPROFILES}","-jar", "-Dserver.port=8082", "-Dserver.address=0.0.0.0"] 