FROM eclipse-temurin:8-jdk

WORKDIR /app

ARG JAR_FILE=target/dev-mentor-backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
