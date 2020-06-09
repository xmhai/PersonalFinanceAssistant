FROM java:8

VOLUME /tmp 
ADD /target/stock-service-0.0.1-SNAPSHOT.jar stock-service.jar
 
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${ENV}", "stock-service.jar"]