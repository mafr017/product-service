FROM maven:3.9.0-eclipse-temurin-17-alpine AS maven

WORKDIR /usr/src/app
#COPY . /usr/src/app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ src/
RUN mvn package -DskipTests

FROM openjdk:17
ARG JAR_FILE=product-service-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app/

COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app

EXPOSE 8181

ENTRYPOINT ["java", "-jar", "product-service-0.0.1-SNAPSHOT.jar"]