FROM openjdk:17-oracle
LABEL authors="ideatec"
ARG port
EXPOSE ${port}
COPY ./target/idea-api-server-0.0.1-SNAPSHOT.jar /usr/local/idea-api-server-0.0.1-SNAPSHOT.jar
RUN chmod +x /usr/local/idea-api-server-0.0.1-SNAPSHOT.jar
WORKDIR /usr/local
ENTRYPOINT ["java", "-jar","-Dpostgres.password=${postgresPassword}", "idea-api-server-0.0.1-SNAPSHOT.jar"]