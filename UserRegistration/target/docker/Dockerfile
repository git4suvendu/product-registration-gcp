FROM java:8-alpine
ADD /UserRegistration-0.0.1.jar //
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker", "-jar", "/UserRegistration-0.0.1.jar"]
