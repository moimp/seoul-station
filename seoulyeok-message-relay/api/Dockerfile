FROM amazoncorretto:11-alpine

ARG FROM_JAR=build/libs/api.jar

COPY ${FROM_JAR} app.jar

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "app.jar"]