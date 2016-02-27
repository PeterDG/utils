REVOKE CONNECT ON DATABASE "testABCD" FROM PUBLIC, test;
select pg_terminate_backend(pid) from pg_stat_activity where datname='testABCD';
DROP DATABASE IF EXISTS "testABCD";
