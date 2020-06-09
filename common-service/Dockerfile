FROM java:8

VOLUME /tmp 
ADD /target/common-service-0.0.1-SNAPSHOT.jar common-service.jar
 
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${ENV}", "common-service.jar"]
