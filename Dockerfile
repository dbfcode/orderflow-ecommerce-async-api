FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY pom.xml .
RUN apk add --no-cache maven && mvn dependency:go-offline -B

COPY src src

CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.profiles=docker"]