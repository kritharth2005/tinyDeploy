# Start with a lightweight Java runtime
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file we just built into the container
# Note: The JAR name depends on your pom.xml, usually it's "tinyDeploy-0.0.1-SNAPSHOT.jar"
COPY target/*.jar app.jar

# Tell Docker to run this command when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]