@echo off
set "JAVA8_HOME=C:\Program Files\Java\jdk-8"
set "JAVA9_HOME=C:\Program Files\Java\jdk-9"
set "java=%JAVA8_HOME%\bin\java"
set "javac=%JAVA8_HOME%\bin\javac"

rem Compile
rem   change this in part 2
rmdir /s /q target && mkdir target
xcopy src\main\resources target /E

echo ""
echo "============================"
echo "= Compiling src\main\java\ ="
echo "============================"
echo ""
"%javac%" -classpath deps\json-20160810.jar;deps\vertx-core-3.3.3.jar;deps\vertx-web-3.3.3.jar ^
          -d target\  ^
          -XDignore.symbol.file ^
          src\main\java\io\snowcamp\papaya\api\*.java ^
          src\main\java\io\snowcamp\papaya\doc\*.java ^
          src\main\java\io\snowcamp\papaya\inmemory\*.java ^
          src\main\java\io\snowcamp\papaya\reflect\*.java ^
          src\main\java\io\snowcamp\papaya\spi\*.java ^
          src\main\java\io\snowcamp\papaya\web\*.java
 echo ""
echo "============================"
echo "= Compiling src\test\java\ ="
echo "============================"
echo ""
"%javac%" -classpath target;deps\json-20160810.jar;deps\junit-4.12.jar ^
          -d target\ ^
          src\test\java\io\snowcamp\papaya\test\*.java
echo ""
echo "======================"
echo "= End of compilation ="
echo "======================"
echo ""

rem Run
rem   change this in part 1
"%java%" -classpath target;deps\vertx-core-3.3.3.jar;deps\vertx-web-3.3.3.jar;deps\vertx-internal-3.3.3.jar ^
         io.snowcamp.papaya.web.ExampleApp
rem %java% -classpath target;deps\json-20160810.jar;deps\junit-4.12.jar;deps\hamcrest-core-1.3.jar ^
rem      org.junit.runner.JUnitCore io.snowcamp.papaya.test.TestSuite
