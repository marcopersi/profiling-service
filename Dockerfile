# Verwende ein leichtgewichtiges JDK-Image
FROM openjdk:17-jdk-slim

# Setze das Arbeitsverzeichnis innerhalb des Containers
WORKDIR /app

# Kopiere die JAR-Datei in den Container
ARG JAR_FILE=target/profiling-service-1.0.0.jar
COPY ${JAR_FILE} app.jar

# Exponiere den Port, auf dem die Spring Boot-Anwendung läuft
EXPOSE 8080

# Führe die Anwendung aus
ENTRYPOINT ["java", "-jar", "app.jar"]
