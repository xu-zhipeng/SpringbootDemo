#!/bin/sh
#项目jar包名称
APP_NAME=data-api-1.0-SNAPSHOT.jar
# /dev/null 不输出日志
nohup java -Xms128m -Xmx256m -jar $APP_NAME --spring.profiles.active=prod > /dev/null 2>&1 &
echo "服务一启动"