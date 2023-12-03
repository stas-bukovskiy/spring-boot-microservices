FROM openjdk:17-jdk-alpine3.13
ENV SERVER_PORT 8005
ENV SPRING_PROFILES_ACTIVE docker
COPY build/libs/analytics-0.0.1.jar /app/analytics.jar
WORKDIR /app
EXPOSE 8005
CMD ["java", "-jar", "analytics.jar"]