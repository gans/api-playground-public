#!/usr/bin/env bash
if [[ "$1" = "" ]]
then
  echo "Usage: $0 <number of change sets to rollback>"
  exit
fi

DRIVER="org.h2.Driver"
URL="jdbc:h2:~/playground;MODE=MYSQL"
USERNAME="sa"
ROLLBACK_COUNT=$1
CHANGE_LOG_FILE="src/main/resources/db/changelog/db.changelog-master.yaml"

mvn resources:resources liquibase:rollback \
    -Dliquibase.driver=${DRIVER} \
    -Dliquibase.url=${URL} \
    -Dliquibase.username=${USERNAME} \
    -Dliquibase.rollbackCount=${ROLLBACK_COUNT} \
    -Dliquibase.changeLogFile=${CHANGE_LOG_FILE}
