FROM openjdk:8-jdk-alpine

COPY /target/account-service-1.0.0.jar account-service.jar
#COPY wait-for-it.sh /wait-for-it.sh 
#RUN chmod +x /wait-for-it.sh

RUN apk add --no-cache tzdata
ENV TZ Asia/Singapore
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENTRYPOINT [ "java", "-jar", "account-service.jar"]