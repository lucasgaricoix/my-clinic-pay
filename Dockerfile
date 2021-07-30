FROM openjdk:11

COPY /build/libs/myclinicpay-0.0.1-SNAPSHOT.jar myclinicpay-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar", "/myclinicpay-0.0.1-SNAPSHOT.jar", "-Dserver.port=8082"]
