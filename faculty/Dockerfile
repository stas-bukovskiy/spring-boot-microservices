FROM openjdk:17-oracle
ENV SERVER_PORT 8001
ENV GRPC_SERVER_PORT 9001
ENV SPRING_PROFILES_ACTIVE docker
COPY build/libs/faculty-0.0.1.jar /app/faculty.jar
WORKDIR /app
EXPOSE 8001
EXPOSE 9001
CMD ["java", "-jar", "faculty.jar"]
