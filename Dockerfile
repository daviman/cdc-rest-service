# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="mdavid@sorintlab.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/cdc-rest-service-1.0-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} cdc-rest-service-1.0-SNAPSHOT.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "cdc-rest-service-1.0-SNAPSHOT.jar" ]
