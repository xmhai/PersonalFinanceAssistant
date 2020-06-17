FROM openjdk:8-jdk-alpine

COPY /target/account-service-0.0.1-SNAPSHOT.jar account-service.jar
COPY wait-for-it.sh /wait-for-it.sh 
RUN chmod +x /wait-for-it.sh

ENTRYPOINT [ "java", "-jar", "account-service.jar"]