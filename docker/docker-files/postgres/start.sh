#!/usr/bin/env bash
#===============================================================================
#
#    AUTHOR: Peter
#
#===============================================================================
CONF="${PG_DATA}/postgresql.conf"
HBA="${PG_DATA}/pg_hba.conf"
DATA="${PG_DATA}"
POSTGRES="/usr/lib/postgresql/${PG_VERSION}/bin/postgres"
#-------------------------------------------------------------------------------
echo "host all  all    0.0.0.0/0  md5" >> "$HBA"
echo "listen_addresses='*'" >> "$CONF"
#-------------------------------------------------------------------------------
#echo "Creating superuser: ${USER}"
#exec postgres
#exec psql postgres postgres
#<<EOF
#CREATE DATABASE test;
#EOF
#-------------------------------------------------------------------------------
#echo "Starting postgres:"
#exec  postgres
#===============================================================================
