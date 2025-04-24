#!/bin/bash
export APP_ENV=development
export DB_URL=jdbc:postgresql://ep-dry-scene-a5t2cstn-pooler.us-east-2.aws.neon.tech/ortodiroverbasso
export DB_USER=ortodiroverbasso_owner
export DB_PASSWORD=npg_7gLUmy3CRQDO
export DB_DRIVER=org.postgresql.Driver
export DB_SSL_MODE=require
export BLOB_READ_WRITE_TOKEN=vercel_blob_rw_dlkUJg8SQ6zN2A3I_mTpU5gzPod3YVAru72DJr8xmV4mn3l


#mvn clean install
# Esegui l'app con Maven
mvn spring-boot:run
