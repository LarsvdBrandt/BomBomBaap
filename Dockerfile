FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /build/target/bombombaap.jar .
COPY players.json .
EXPOSE 8080
ENV PORT=8080
CMD ["java", "-jar", "bombombaap.jar"]
