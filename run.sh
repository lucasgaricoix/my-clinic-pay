#!/bin/bash
nohup java -jar build/libs/myclinicpay-0.0.1-SNAPSHOT.jar -Dserver.port=8082 -Duser.timezone=America/Sao_Paulo \
  && caddy start
