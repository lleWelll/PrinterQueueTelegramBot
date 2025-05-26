FROM bellsoft/liberica-openjdk-alpine:17
WORKDIR /PrinterBot
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]