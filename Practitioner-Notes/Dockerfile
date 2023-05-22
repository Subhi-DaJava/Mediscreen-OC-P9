FROM openjdk:17-alpine
WORKDIR /practitionner-notes

COPY build.gradle gradlew settings.gradle ./
COPY gradle/ gradle/
RUN ./gradlew clean build

COPY src/ src/
RUN ./gradlew bootJar

EXPOSE 8082
CMD ["java", "-jar", "build/libs/practitioner-notes.jar"]