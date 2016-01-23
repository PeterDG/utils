REVOKE CONNECT ON DATABASE "p_dbName" FROM PUBLIC, p_dbOwner;
select pg_terminate_backend(pid) from pg_stat_activity where datname='p_dbName';