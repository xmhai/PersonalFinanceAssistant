FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=root

COPY config-file.cnf /etc/mysql/conf.d/config-file.cnf
COPY 0-init.sql /docker-entrypoint-initdb.d/
