FROM openjdk:17-jdk-slim
EXPOSE 8089
ADD http://192.168.56.100:8081/repository/maven-releases/com/example/gestion-station-skii/1.0.0/gestion-station-skii-1.0.0.jar gestion-station-skii-1.0.0.jar
ENTRYPOINT ["java","-jar","/gestion-station-skii-1.0.0.jar"]
