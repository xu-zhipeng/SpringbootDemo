@echo on
title 正在停止grap-order
color 0A
taskkill -f -t -im javaw.exe
echo ---停止成功---
pause
exit 