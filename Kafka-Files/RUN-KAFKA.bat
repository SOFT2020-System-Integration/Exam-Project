@echo off

SET MY_PATH=C:\kafka
SET "condition=true"

if EXIST "%MY_PATH%" (
    SET "condition=true"
) else (
    @echo --------------------------------------------------------------------
    @echo Kafka not found under %MY_PATH%
    @echo Running Kafka Setup, please wait, this might take a few minutes...
    @echo WARNING: Do not close the program while it's running setup
    @echo --------------------------------------------------------------------
    START "runas /user:administrator" /WAIT _unzip.bat
    SET "condition=true"
)


:PROMPT
set /P c="Do you want to clear kafka and zookeeper data before running the service? [Y/N] : "

if /I "%c%" EQU "Y" goto :run-with-log-clear
if /I "%c%" EQU "N" goto :run-without-log-clear

goto :choice

:run-with-log-clear
    echo Clearing Data...
    START /WAIT _clear-data.bat
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

