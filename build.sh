#!/bin/bash
export JAVA8_HOME=/usr/jdk/jdk1.8.0_65
export JAVA9_HOME=/usr/jdk/jdk-9
export java=$JAVA9_HOME/bin/java
export javac=$JAVA8_HOME/bin/javac

mkdir -p target && rm -rf target/*
cp -r src/main/resources/* target/
$javac -classpath deps/json-20160810.jar:deps/vertx-core-3.3.3.jar:deps/vertx-web-3.3.3.jar \
       -d target/ \
       $(find src/main/java/ -name "*.java")
$javac -classpath target:deps/json-20160810.jar:deps/junit-4.12.jar \
       -d target/ \
       $(find src/test/java/ -name "*.java")

$java -classpath target:deps/vertx-core-3.3.3.jar:deps/vertx-web-3.3.3.jar:deps/vertx-internal-3.3.3.jar \
      --add-modules java.xml.bind --add-opens java.base/java.nio=ALL-UNNAMED --add-opens java.base/sun.nio.ch=ALL-UNNAMED \
      --add-exports java.base/sun.net.dns=ALL-UNNAMED -Dsun.reflect.debugModuleAccessChecks=true \
      io.snowcamp.papaya.web.ExampleApp

#$java -classpath target:deps/json-20160810.jar:deps/junit-4.12.jar:deps/hamcrest-core-1.3.jar \
#      org.junit.runner.JUnitCore io.snowcamp.papaya.test.TestSuite

