
# Fetching latest version of Java
FROM openjdk:11-jdk-slim AS build
# Set working directory
WORKDIR /app

# Copy the application files
COPY . .

# Final image
FROM openjdk:11-jre-slim
# Setting up work directory
WORKDIR /app

# Copy the jar file into our app
COPY --from=build /target/spring-with-nextjs-0.0.1-SNAPSHOT.jar .

# Exposing port 8080
EXPOSE 8080

# Starting the application
CMD ["java", "-jar", "spring-with-nextjs-0.0.1-SNAPSHOT.jar"]