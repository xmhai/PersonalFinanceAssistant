FROM openjdk:8-jdk-alpine

COPY /target/common-service-0.0.1-SNAPSHOT.jar common-service.jar
COPY wait-for-it.sh /wait-for-it.sh 
RUN chmod +x /wait-for-it.sh

RUN apk add --no-cache tzdata
ENV TZ Asia/Singapore
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENTRYPOINT [ "java", "-jar", "common-service.jar"]

