# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="mdavid@sorintlab.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG APP_HOME=/opt/cdc-app
# Add the application's jar to the container
RUN mkdir -p ${APP_HOME}/lib

ADD target/cdc-rest-service-1.0-SNAPSHOT.jar.original ${APP_HOME}/cdc-rest-service-1.0-SNAPSHOT.jar
ADD target/cdc-rest-service-1.0-SNAPSHOT-cdc-rest-service-dist-assembly.tar.gz /tmp/cdc-rest-service-1.0-SNAPSHOT-cdc-rest-service-dist-assembly.tar.gz

RUN cd ${APP_HOME}/lib/ && tar -xzf /tmp/cdc-rest-service-1.0-SNAPSHOT-cdc-rest-service-dist-assembly.tar.gz
RUN rm -f /tmp/cdc-rest-service-1.0-SNAPSHOT-cdc-rest-service-dist-assembly.tar.gz

# Run the jar file
ENTRYPOINT ["java", "-classpath", "${APP_HOME}/cdc-rest-service-1.0-SNAPSHOT.jar:${APP_HOME}/lib", "-jar", "${APP_HOME}/cdc-rest-service-0.0.1-SNAPSHOT.jar", "com.sorint.demo.service.Application"]
