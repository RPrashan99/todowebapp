# Use an official JDK image
FROM eclipse-temurin:17-jdk AS builder

# Set working directory
WORKDIR /app

# Copy Maven build files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src ./src

# Build the project
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Run the built JAR
FROM eclipse-temurin:17-jdk

# Copy the built JAR to container
COPY --from=builder /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
