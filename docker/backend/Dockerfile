# Build stage
FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /build
COPY ../../backend/pom.xml .
RUN mvn dependency:go-offline
COPY ../../backend/src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM amazoncorretto:17
ARG PROFILE=dev
ARG APP_VERSION=1.0.0

WORKDIR /app
COPY --from=build /build/target/backend-*.jar /app/

EXPOSE 9080

ENV DB_URL=jdbc:postgresql://host.docker.internal:5432/bd_payments
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}

CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource=${DB_URL} backend-${JAR_VERSION}.jar