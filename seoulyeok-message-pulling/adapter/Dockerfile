FROM amazoncorretto:11-alpine

ARG FROM_JAR=build/libs/message-puller.jar

COPY ${FROM_JAR} app.jar

EXPOSE 5000

ENTRYPOINT ["java", "-XX:MaxRAM=256M", "-Xmx128M", "-Xss256k", "-Xms128M", "-jar","app.jar"]
