# 1. Specify the base image
FROM eclipse-temurin:25-jre

# 2. Set the working directory
WORKDIR /app

# 3. Copy JAR into the image
COPY target/HealthApp-0.0.1-SNAPSHOT.jar app.jar

# 4. EXPOSE: Document the application port (not publish yet)
EXPOSE 8080

# 5. ENTRYPOINT: run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

