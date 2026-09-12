FROM gradle:8.10.2-jdk17 AS build
WORKDIR /workspace

COPY gradlew .
COPY gradlew.bat .
COPY gradle gradle
COPY settings.gradle.kts build.gradle.kts gradle.properties ./
COPY buildSrc ./buildSrc
COPY application ./application
COPY adapters ./adapters
COPY domain ./domain
COPY planet-assessment-api ./planet-assessment-api

RUN sed -i 's/\r$//' gradlew && chmod +x gradlew && bash ./gradlew :planet-assessment-api:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/planet-assessment-api/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]