FROM openjdk:23
LABEL authors="muham"

VOLUME /tmp
VOLUME /logs

WORKDIR /app

COPY ./target/book-service.jar /app

EXPOSE 8080

CMD ["java", "-jar", "book-service.jar"]