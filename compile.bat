@echo off
javac -cp "C:\Users\User\Documents\Fianarana\S4\Mr Naina\servlet.jar" -d bin src\controller\*.java
jar cvf "C:\Users\User\Documents\Fianarana\S4\Mr Naina\Framework.jar" -C bin .
