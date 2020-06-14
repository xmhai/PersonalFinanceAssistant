FROM openjdk:8-jdk-alpine

COPY /target/stock-service-0.0.1-SNAPSHOT.jar stock-service.jar
COPY wait-for-it.sh /wait-for-it.sh 
RUN chmod +x /wait-for-it.sh
