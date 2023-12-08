FROM openjdk:17-oracle
ENV SERVER_PORT 8010
ENV SPRING_PROFILES_ACTIVE docker
COPY build/libs/gateway-0.0.1.jar /app/gateway.jar
WORKDIR /app
EXPOSE 8010
CMD ["java", "-jar", "gateway.jar"]
