FROM maven:3.8.4-openjdk-17
WORKDIR /foyer
EXPOSE 8089

# Assure-toi que le fichier JAR dans target/ est bien généré avec ce nom exact
COPY target/foyer-3.0.0.jar foyer-3.0.0.jar

ENTRYPOINT ["java", "-jar", "foyer-3.0.0.jar"]
