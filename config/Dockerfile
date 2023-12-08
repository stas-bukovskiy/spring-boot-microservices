FROM openjdk:17-oracle
ENV SERVER_PORT 8888
ENV SPRING_PROFILES_ACTIVE docker,native
COPY build/libs/config-0.0.1.jar /app/config.jar
WORKDIR /app
EXPOSE 8888
CMD ["java", "-jar", "config.jar"]
