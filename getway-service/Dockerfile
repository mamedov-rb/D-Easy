#
#FROM gradle:jdk11 as builder
#WORKDIR /home/gradle/src
#COPY --chown=gradle:gradle . /home/gradle/src
#RUN gradle build --no-daemon -i --stacktrace -x test

FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine-slim
RUN mkdir /app

#COPY --from=builder /home/gradle/src/build/libs/*.jar /app/zuul-service.jar
COPY /build/libs/*.jar /app/zuul-service.jar

COPY ./wait-for.sh /app/wait-for.sh
RUN chmod +x /app/wait-for.sh
