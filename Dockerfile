
# Fetching latest version of Java
FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
# Copy app
COPY src ./src
# Exposing port 8080
EXPOSE 8080
# Starting the application
CMD ["./mvnw", "spring-boot:run"]


