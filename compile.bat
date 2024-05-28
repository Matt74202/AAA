@echo off
javac -cp "C:\Program Files\Apache Software Foundation\Tomcat 10.1\lib\servlet-api.jar" -d bin src\controller\*.java src\util\*.java src\annotation\*.java
jar cvf "C:\Users\User\Documents\Fianarana\S4\Mr Naina\Framework.jar" -C bin .
