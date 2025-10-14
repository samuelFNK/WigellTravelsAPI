FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/WigellTravelsAPI-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8585
ENTRYPOINT ["java","-jar","app.jar"]