FROM gradle:jdk21 AS build

WORKDIR /usr/src/app

COPY gradlew .
COPY gradle ./gradle

COPY build.gradle settings.gradle ./
COPY src ./src

RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test --no-daemon --parallel --refresh-dependencies=false

FROM openjdk:21-slim

WORKDIR /app

COPY --from=build /usr/src/app/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
