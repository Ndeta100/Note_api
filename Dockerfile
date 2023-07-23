# Build the application
RUN ./mvnw package -DskipTests
# Fetching latest version of Java
FROM openjdk:17

# Setting up work directory
WORKDIR /app

# Copy the jar file into our app
COPY --from=build /app/target/spring-with-nextjs-0.0.1-SNAPSHOT.jar .

# Exposing port 8080
EXPOSE 8080

# Starting the application
CMD ["java", "-jar", "spring-with-nextjs-0.0.1-SNAPSHOT.jar"]