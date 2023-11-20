FROM openjdk:17-jdk-alpine3.13

ENV SERVER_PORT 8001
ENV SPRING_PROFILES_ACTIVE docker

COPY build/libs/faculty-service-0.0.1.jar /app/

WORKDIR /app

EXPOSE 8001

CMD ["java", "-jar", "faculty-service-0.0.1.jar"]
