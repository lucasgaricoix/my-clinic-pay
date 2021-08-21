FROM openjdk:11

COPY /build/libs/myclinicpay-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar", "-Dserver.port=8082", "-Duser.timezone=America/Sao_Paulo"]
