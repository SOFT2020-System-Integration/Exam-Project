@echo off
SET MY_PATH=C:\kafka
        START "runas /user:administrator" cmd /K "cd "%MY_PATH%\bin\windows" & zookeeper-server-start.bat ../../config/zookeeper.properties"
    timeout /t 5 /nobreak
	    START "runas /user:administrator" cmd /K "cd "%MY_PATH%\bin\windows" & kafka-server-start.bat ../../config/server.properties"
    exit
