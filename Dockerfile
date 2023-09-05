FROM openjdk:11

COPY . server

WORKDIR "server"

RUN chmod +x gradlew

RUN ./gradlew build
