#!/bin/sh
#项目jar包名称
APP_NAME=/tongyu/docker-images/submission-1.0.jar
#重启命令
pid=`ps -ef | grep $APP_NAME | grep -v grep |awk '{print $2}'`
if [ $pid ]
then
    echo :App  is  running pid=$pid
    echo :kill -9 $pid
    kill -9 $pid
else
    echo Application is already stopped
fi
echo "Stop 本服务结束"