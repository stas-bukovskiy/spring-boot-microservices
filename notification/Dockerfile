FROM openjdk:17-jdk-alpine3.13
ENV SERVER_PORT 8004
ENV SPRING_PROFILES_ACTIVE docker
COPY build/libs/notification-0.0.1.jar /app/notification.jar
WORKDIR /app
EXPOSE 8004
CMD ["java", "-jar", "notification.jar"]

