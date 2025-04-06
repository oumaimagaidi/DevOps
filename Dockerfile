FROM maven:3.8.4-openjdk-17
WORKDIR /foyer
EXPOSE 8089

# Copy the JAR file into the container
COPY target/foyer-3.0.0.jar foyer-3.0.0.jar

# List the files in the /foyer directory (for debugging purposes)
RUN ls -l /foyer

ENTRYPOINT ["java", "-jar", "/foyer-3.0.0.jar"]
