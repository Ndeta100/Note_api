
# Fetching latest version of Java
FROM eclipse-temurin:17-jdk-alpine
# Set working directory
WORKDIR /app

# Copy the application files
COPY . .
# Build the application
RUN ./mvnw package -DskipTests
# Final image
FROM eclipse-temurin:17-jdk-alpine
# Setting up work directory
WORKDIR /app

# Copy the jar file into our app
COPY  /app/target/spring-with-nextjs-0.0.1-SNAPSHOT.jar .

# Exposing port 8080
EXPOSE 8080

# Starting the application
CMD ["java", "-jar", "spring-with-nextjs-0.0.1-SNAPSHOT.jar"]