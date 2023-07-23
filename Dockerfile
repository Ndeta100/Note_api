ARG JAVA_VERSION=17
# Fetching latest version of Java
FROM openjdk:${JAVA_VERSION}

# Copy the jar file into our app
COPY  /target/spring-with-nextjs-0.0.1-SNAPSHOT.jar note-api.jar

# Exposing port 8080
EXPOSE 8080

# Starting the application
CMD ["java", "-jar", "/note-api.jar"]

