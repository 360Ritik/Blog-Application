FROM eclipse-temurin:17

LABEL mentainer="ritikumar360@gmail.com"

WORKDIR /app

COPY target/blog-application-0.0.1-SNAPSHOT.jar  /app/blog-application-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "blog-application-0.0.1-SNAPSHOT.jar"]

