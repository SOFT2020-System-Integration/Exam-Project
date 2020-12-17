@echo off
SET MY_PATH=C:\



:PROMPT
set /P c="Are you sure you wish to remove Kafka from "%MY_PATH%kafka" ? THIS CHANGE IS IRREVERSIBLE [Y/N] : "


if /I "%c%" EQU "Y" goto :delete-kafka
if /I "%c%" EQU "N" goto :abort

goto :choice

:delete-kafka
    @echo Deleting Kafka....
    rmdir /s /q "%MY_PATH%\kafka\"
    exit


:abort
    @echo Exiting...
    exit