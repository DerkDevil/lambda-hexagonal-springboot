#!/usr/bin/env bash
set -euo pipefail

echo "Esperando a que LocalStack TCP:4566 esté arriba"
# bucle hasta que /dev/tcp/localstack/4566 acepte conexión
while ! bash -c "echo > /dev/tcp/localstack/4566"; do
  echo "    LocalStack aún no está listo. Reintentando en 2s…"
  sleep 2
done

echo "LocalStack disponible. Creando secreto en Secrets Manager…"

cat > secrets.json <<EOF
{
  "username": "admin",
  "password": "password",
  "url": "jdbc:postgresql://local-postgres:5432/testdb"
}
EOF

awslocal secretsmanager create-secret \
  --name my-db-secret \
  --description "LocalStack Secret for PostgreSQL" \
  --secret-string file://secrets.json

echo "Secreto ‘my-db-secret’ creado con éxito."
