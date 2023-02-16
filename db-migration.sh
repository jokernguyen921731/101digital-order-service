#!/bin/sh

cd /app && /app/liquibase \
    --url $POSTGRESQL_URI \
    --username $POSTGRESQL_USER \
    --password $POSTGRESQL_PASSWORD \
    --driver org.postgresql.Driver \
    --changeLogFile src/main/resources/db/migrations.yaml \
    --classpath postgresql-42.2.18.jar \
    --logLevel info \
    update
