FROM openjdk:17-oracle
ENV SERVER_PORT 8000
ENV GRPC_SERVER_PORT 9000
ENV SPRING_PROFILES_ACTIVE docker
COPY build/libs/auth-0.0.1.jar /app/auth.jar
WORKDIR /app
EXPOSE 8000
EXPOSE 9000
CMD ["java", "-jar", "auth.jar"]

