FROM openjdk:11

COPY . server

WORKDIR "server"

RUN chmod +x gradlew

RUN ./gradlew assemble

ENTRYPOINT ["java", "-jar", "build/libs/myclinicpay-0.0.1-SNAPSHOT.jar", "-Dserver.port=8082", "-Duser.timezone=America/Sao_Paulo"]
