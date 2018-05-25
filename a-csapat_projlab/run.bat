@ECHO OFF

set PATH=%PATH%;C:\Program Files\Java\jdk1.8.0_121\bin
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_121
set CLASSPATH=%CLASSPATH%;

javac -d bin src/src/*.java

cd bin
java src.StorehouseView

cd ..
