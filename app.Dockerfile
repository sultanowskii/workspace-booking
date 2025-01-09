FROM amazoncorretto:21.0.4

WORKDIR /app

COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew

ENTRYPOINT ["./gradlew", "app:run"]
