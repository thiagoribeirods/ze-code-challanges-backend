FROM gradle:8.8.0-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test

FROM openjdk:latest
VOLUME [ "/tmp" ]
COPY --from=build /app/build/libs /app/libs/
WORKDIR /app/libs
ENTRYPOINT [ "java", "-jar", "ze-code-challenges-backend-1.jar" ]