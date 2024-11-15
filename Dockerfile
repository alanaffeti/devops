FROM openjdk:11-jre-slim

EXPOSE 8089

WORKDIR /app

RUN apt-get update && apt-get install -y curl

RUN curl -o achat-1.0.jar -L "http://192.168.50.4:8081/repository/maven-releases/tn/esprit/spring/gestion-station-ski/1.0/gestion-station-ski-1.0.jar"


ENTRYPOINT ["java", "-jar", "achat-1.0.jar"]
