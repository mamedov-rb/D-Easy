
#FROM gradle:jdk11 as builder
#COPY --chown=gradle:gradle . /home/gradle/src
#WORKDIR /home/gradle/src
#RUN gradle build --no-daemon -i --stacktrace -x test

#FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine-slim
FROM adoptopenjdk/openjdk11:alpine-jre
RUN mkdir /app
#COPY --from=builder /home/gradle/src/build/libs/*.jar /app/discovery-service.jar
COPY /build/libs/*.jar /app/discovery-service.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=development", "/app/discovery-service.jar"]
