
# Fetching latest version of Java
FROM eclipse-temurin:17-jdk-alpine
# Set working directory
WORKDIR /app

# Copy the jar file into our app
COPY  /target/spring-with-nextjs-0.0.1-SNAPSHOT.jar note-api.jar

# Exposing port 8080
EXPOSE 8080

# Starting the application
CMD ["java", "-jar", "note-api.jar"]