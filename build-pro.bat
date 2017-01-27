@echo off
set "JAVA9_HOME=C:\Program Files\Java\jdk-9"
set "java=%JAVA9_HOME%\bin\java"

set "PRO_HOME=%USERPROFILE%\Documents\GitHub\pro\target\pro"

echo ""
echo "============================"
echo "= Building with pro        ="
echo "============================"
echo ""
call "%PRO_HOME%\bin\pro.bat"
echo ""
echo "======================"
echo "= End of Build       ="
echo "======================"
echo ""
if ERRORLEVEL 1 (
      exit /b 1
)
rem "%java%" --module-path target/test/artifact;target/main/artifact;deps  ^
rem          -classpath deps/hamcrest-core-1.3.jar    ^
rem          -m papaya/io.snowcamp.papaya.test.Run
