
FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine-slim
RUN mkdir /app

COPY /build/libs/*.jar /app/order-service.jar

COPY ./wait-for.sh /app/wait-for.sh
RUN chmod +x /app/wait-for.sh
