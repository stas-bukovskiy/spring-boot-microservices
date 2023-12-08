FROM openjdk:17-oracle
ENV SERVER_PORT 8761
ENV SPRING_PROFILES_ACTIVE docker
COPY build/libs/admin-panel-0.0.1.jar /app/admin-panel.jar
WORKDIR /app
EXPOSE 8761
CMD ["java", "-jar", "admin-panel.jar"]
