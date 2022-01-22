FROM openjdk:8-jdk-alpine

#https://stackoverflow.com/questions/30464204/spring-boot-docker-using-volume-tmp
VOLUME /tmp

COPY /target/stock-service-1.0.0.jar stock-service.jar
#COPY wait-for-it.sh /wait-for-it.sh
#RUN chmod +x /wait-for-it.sh

RUN apk add --no-cache tzdata
ENV TZ Asia/Singapore
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

#EXPOSE 8000

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar stock-service.jar"]
#ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n", "-jar", "stock-service.jar"]
