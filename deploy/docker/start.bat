%on ���������������ʾ����%
@echo on
%���ڱ���%
title ��������grap-order
%�޸�������ɫ%
color 0A
%��̨����javaw%
start javaw -jar grap-order.jar --spring.profiles.active=prod --scheduled.cron="*/5 * * * * ?"
echo ----��̨�����ɹ�----
pause
exit