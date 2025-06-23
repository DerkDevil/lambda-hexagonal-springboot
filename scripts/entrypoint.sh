#!/usr/bin/env bash
set -euo pipefail

echo "Esperando a que LocalStack esté arriba"
while ! bash -c "echo > /dev/tcp/localstack/4566"; do sleep 2; done

echo "LocalStack arriba - creando secretos"
awslocal --endpoint-url=http://localstack:4566 secretsmanager create-secret \
  --name my-db-secret --description "Credenciales Postgres" \
  --secret-string file:///scripts/secrets.json

echo "Levantando BD"
while ! bash -c "echo > /dev/tcp/local-postgres/5432"; do sleep 2; done

echo "Compilando proyecto"
cd /workspace
mvn clean package -DskipTests

echo "Construyendo con sam"
sam build

echo "Listo para ejecución"
tail -f /dev/null
