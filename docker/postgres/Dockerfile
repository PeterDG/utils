FROM postgres:9.5
MAINTAINER Peter

ENV PG_VERSION 9.5
ENV POSTGRES_USER test
ENV POSTGRES_PASSWORD test
ENV PGDATA /var/lib/postgresql/data/main
ENV POSTGRES_DB test

ADD start.sh /docker-entrypoint-initdb.d/start.sh
ADD setup-tables.sql /docker-entrypoint-initdb.d/setup-tables.sql

