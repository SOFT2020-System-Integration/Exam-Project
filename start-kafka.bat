@echo off

SET MY_PATH=C:\kafka
SET "condition=true"

if EXIST "%MY_PATH%" (
    SET "condition=true"
) else (
 	echo Folder named "Kafka" doesn't exist under %MY_PATH%
	@pause
    exit
)

:PROMPT
set /P c="Do you want to clear kafka and zookeeper data before continuing? [Y/N] : "
if /I "%c%" EQU "Y" goto :run-with-log-clear
if /I "%c%" EQU "N" goto :run-without-log-clear
goto :choice

:run-with-log-clear
    echo Clearing Data...
    rmdir /s /q "%MY_PATH%\data\"
    timeout /t 2 /nobreak
    echo Starting Zookeeper...
        START "runas /user:administrator" cmd /K "cd "%MY_PATH%\bin\windows" & zookeeper-server-start.bat ../../config/zookeeper.properties"
    timeout /t 5 /nobreak
    echo Starting Kafka...
	    START "runas /user:administrator" cmd /K "cd "%MY_PATH%\bin\windows" & kafka-server-start.bat ../../config/server.properties"
    timeout /t 5 /nobreak
    exit


:run-without-log-clear
    echo Starting Zookeeper...
        START "runas /user:administrator" cmd /K "cd "%MY_PATH%\bin\windows" & zookeeper-server-start.bat ../../config/zookeeper.properties"
    timeout /t 5 /nobreak
    echo Starting Kafka...
	    START "runas /user:administrator" cmd /K "cd "%MY_PATH%\bin\windows" & kafka-server-start.bat ../../config/server.properties"
    timeout /t 5 /nobreak
    exit

