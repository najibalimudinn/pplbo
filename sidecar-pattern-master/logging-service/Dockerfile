FROM openjdk:23
LABEL authors="muham"

VOLUME /tmp
VOLUME /logs

WORKDIR /app

COPY ./target/log-service.jar /app

EXPOSE 8081

CMD ["java", "-jar", "log-service.jar"]