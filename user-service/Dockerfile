FROM openjdk:17-jdk-alpine3.13

ENV SERVER_PORT 8000
ENV SPRING_PROFILES_ACTIVE docker

COPY build/libs/user-service-0.0.1.jar /app/

WORKDIR /app

EXPOSE 8000

CMD ["java", "-jar", "user-service-0.0.1.jar"]

#RUN addgroup -S demo && adduser -S demo -G demo

