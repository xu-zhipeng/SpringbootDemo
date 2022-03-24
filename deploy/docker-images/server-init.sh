#!/bin/sh
export SERVER_PORT=7070
export POSTGRES_HOST="localhost"
export POSTGRES_PORT=5432
export POSTGRES_DB="bct"
export POSTGRES_USERNAME="postgres"
export POSTGRES_PASSWORD="postgres"
export LOG_PATH=/home/tongyu/csi-trans-v3
#项目jar包名称
APP_NAME=/tongyu/docker-images/submission-1.0.jar
# /dev/null 不输出日志
nohup java -Xms128m -Xmx256m -jar $APP_NAME --spring.profiles.active=prod > /dev/null 2>&1 &
echo "服务一启动"