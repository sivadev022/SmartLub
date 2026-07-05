#!/bin/sh
set -e

if [ -z "$SPRING_DATASOURCE_URL" ] && [ -n "$DATABASE_URL" ]; then
  case "$DATABASE_URL" in
    jdbc:postgresql://*)
      export SPRING_DATASOURCE_URL="$DATABASE_URL"
      ;;
    postgres://*|postgresql://*)
      database_url="${DATABASE_URL#postgres://}"
      database_url="${database_url#postgresql://}"
      database_host_and_name="${database_url#*@}"
      export SPRING_DATASOURCE_URL="jdbc:postgresql://${database_host_and_name}"
      ;;
  esac
fi

exec java -jar /app/app.jar
