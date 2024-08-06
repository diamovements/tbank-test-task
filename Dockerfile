FROM openjdk:19-jdk
WORKDIR /app
COPY target/translator-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

