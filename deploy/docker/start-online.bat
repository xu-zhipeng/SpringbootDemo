@echo on
title ����grap-order
color 0A
start /b java -jar grap-order.jar --spring.profiles.active=prod --scheduled.cron="*/5 * * * * ?"