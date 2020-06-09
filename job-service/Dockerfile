FROM java:8

VOLUME /tmp 
ADD /target/job-service-0.0.1-SNAPSHOT.jar job-service.jar
 
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${ENV}", "job-service.jar"]