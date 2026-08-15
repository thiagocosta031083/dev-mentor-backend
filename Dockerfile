FROM maven:3.9.15-eclipse-temurin-25 AS build
WORKDIR /build
COPY pom.xml mvnw mvnw.cmd ./
COPY .mvn .mvn
RUN mvn -q -DskipTests dependency:go-offline
COPY src src
RUN mvn -q clean package

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /build/target/dev-mentor-backend-0.0.1-SNAPSHOT.jar app.jar
RUN groupadd --system app && useradd --system --gid app app && chown app:app /app/app.jar
USER app
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
