FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/blog-application-0.0.1-SNAPSHOT.jar blog-application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","blog-application.jar"]



#FROM eclipse-temurin:17
#
#LABEL mentainer="ritikumar360@gmail.com"
#
#WORKDIR /app
#
#COPY target/blog-application-0.0.1-SNAPSHOT.jar  /app/blog-application-0.0.1-SNAPSHOT.jar
#
#ENTRYPOINT ["java", "-jar", "blog-application-0.0.1-SNAPSHOT.jar"]

