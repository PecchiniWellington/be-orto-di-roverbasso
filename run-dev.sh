#!/bin/bash
export APP_ENV=development
export DB_URL=jdbc:postgresql://ep-dry-scene-a5t2cstn-pooler.us-east-2.aws.neon.tech/ortodiroverbasso
export DB_USER=ortodiroverbasso_owner
export DB_PASSWORD=npg_3JZeE4oTgysu
export DB_DRIVER=org.postgresql.Driver
export DB_SSL_MODE=require

# Esegui l'app con Maven
mvn spring-boot:run
