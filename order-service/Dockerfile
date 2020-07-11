
FROM adoptopenjdk/openjdk11:alpine-jre
RUN mkdir /app

COPY /build/libs/*.jar /app/order-service.jar

COPY ./wait-for.sh /app/wait-for.sh
RUN chmod +x /app/wait-for.sh
