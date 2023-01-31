FROM adoptopenjdk/openjdk11
CMD ["./gradlew", "clean", "build"]
ARG JAR_FILE_PATH=p-application/build/libs/p-application-1.0SNAPSHOT.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]