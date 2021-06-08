FROM postgres:11-alpine

COPY tables.sql /docker-entrypoint-initdb.d/