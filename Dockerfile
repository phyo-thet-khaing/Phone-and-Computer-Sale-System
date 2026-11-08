# Step 1 : use an official Java runtime as the base image
FROM eclipse-temurin:21-jdk

# Step 2: Set working directory inside container
WORKDIR /app

# Step 3: Maintainer label
LABEL maintainer="javaguides.net"

# Step 4: Copy the JAR file from target folder
COPY target/Spring-Project-0.0.1-SNAPSHOT.jar app.jar

# Step 5: Expose application port
EXPOSE 8080

# Step 6: Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
