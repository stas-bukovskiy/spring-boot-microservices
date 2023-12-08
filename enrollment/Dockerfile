FROM openjdk:17-oracle
ENV SERVER_PORT 8003
ENV SPRING_PROFILES_ACTIVE docker
COPY build/libs/enrollment-0.0.1.jar /app/enrollment.jar
WORKDIR /app
EXPOSE 8003
CMD ["java", "-jar", "enrollment.jar"]
