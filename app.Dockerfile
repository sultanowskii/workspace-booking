FROM amazoncorretto:17.0.12

WORKDIR /app

COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew app:build

FROM amazoncorretto:17.0.12

WORKDIR /app

COPY --from=0 /app/app/build/libs/app.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]
