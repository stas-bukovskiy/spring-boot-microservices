FROM openjdk:17-jdk-alpine3.13
ENV SERVER_PORT 8761
ENV SPRING_PROFILES_ACTIVE docker
COPY build/libs/eureka-0.0.1.jar /app/eureka.jar
WORKDIR /app
EXPOSE 8761
CMD ["java", "-jar", "eureka.jar"]
