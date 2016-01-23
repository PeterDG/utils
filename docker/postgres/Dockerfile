FROM postgres:9.4
MAINTAINER Peter

ENV PG_VERSION 9.4
ENV POSTGRES_USER postgresUnitTest
ENV POSTGRES_PASSWORD postgresUnitTest
ENV PGDATA /var/lib/postgresql/data/main
ENV POSTGRES_DB postgresUnitTest

ADD start.sh /docker-entrypoint-initdb.d/start.sh
ADD setup-tables.sql /docker-entrypoint-initdb.d/setup-tables.sql

