# Use an official OpenJDK image as the base
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory in the container
# WORKDIR /app

# Copy the JAR file into the container
COPY target/catalog-service.jar catalog-service.jar

# Expose the application's port (replace with your app's port if different)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "catalog-service.jar"]
