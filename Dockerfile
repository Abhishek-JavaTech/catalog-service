# Use an official OpenJDK image as the base
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory in the container
# WORKDIR /app

# Copy the JAR file into the container
COPY target/CatalogService-0.0.1-SNAPSHOT.jar CatalogService-0.0.1-SNAPSHOT.jar

# Expose the application's port (replace with your app's port if different)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "CatalogService-0.0.1-SNAPSHOT.jar"]
