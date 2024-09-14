FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /app

COPY . /app

RUN mvn clean package -DskipTests

FROM openjdk:19-jdk
WORKDIR /app

COPY --from=builder /app/target/translator-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

