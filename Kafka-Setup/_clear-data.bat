@echo off
@echo Clearing Kafka Data
SET MY_PATH=C:\kafka
rmdir /s /q "%MY_PATH%\data\"
exit