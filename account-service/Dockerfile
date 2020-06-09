FROM java:8

VOLUME /tmp 
ADD /target/account-service-0.0.1-SNAPSHOT.jar account-service.jar
 
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${ENV}", "account-service.jar"]