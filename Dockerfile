FROM gradle:jdk17 AS builder
LABEL authors="lynxiox"

WORKDIR /app

COPY . .

RUN gradle clean build -x test

FROM openjdk:17-alpine AS runtime
LABEL authors="lynxiox"

COPY --from=builder /app/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app/app.jar"]

