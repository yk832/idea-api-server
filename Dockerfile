#FROM openjdk:17-oracle
FROM amazoncorretto:17-alpine-jdk
EXPOSE 8080
COPY ./target/idea-api-server-*.jar /app/idea-api-server.jar
RUN chmod +x /app/idea-api-server.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "-Dfile.encoding=UTF-8", "idea-api-server.ja

r"]