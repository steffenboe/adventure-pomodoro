FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the application to the container
COPY target/adventure-pomodoro-0.0.1-SNAPSHOT.jar /app

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "adventure-pomodoro-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]