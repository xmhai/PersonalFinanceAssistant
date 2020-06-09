FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} common-service.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${ENV}", "/common-service.jar"]