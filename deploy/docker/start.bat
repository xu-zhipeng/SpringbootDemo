%on 开启输出，可以显示错误%
@echo on
%窗口标题%
title 正在启动grap-order
%修改字体颜色%
color 0A
%后台启动javaw%
start javaw -jar grap-order.jar --spring.profiles.active=prod --scheduled.cron="*/5 * * * * ?"
echo ----后台启动成功----
pause
exit