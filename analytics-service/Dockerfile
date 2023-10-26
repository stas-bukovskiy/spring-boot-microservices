FROM openjdk:17-jdk-alpine3.13

ENV SERVER_PORT 8005
ENV SPRING_PROFILES_ACTIVE docker

COPY build/libs/analythics-service-0.0.1.jar /app/

WORKDIR /app

EXPOSE 8001

CMD ["java", "-jar", "analythics-service-0.0.1.jar"]