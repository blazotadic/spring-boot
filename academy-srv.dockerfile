FROM adoptopenjdk/openjdk11

RUN mkdir /config

COPY target/*.jar /app.jar

CMD ["java", "-jar", "app.jar"]
