#!/bin/bash
#export JAVA_HOME=/usr/jdk/jdk1.8.0_65/
export JAVA_HOME=/usr/jdk/jdk-9/
export java=$JAVA_HOME/bin/java
export javac=$JAVA_HOME/bin/javac

mkdir target
$javac -classpath deps/json-20160810.jar \
       -d target/ \
       $(find src/main/java/ -name "*.java")
$javac -classpath target:deps/json-20160810.jar:deps/junit-4.12.jar \
       -d target/ \
       $(find src/test/java/ -name "*.java")
       
$java -classpath target:deps/json-20160810.jar:deps/junit-4.12.jar:deps/hamcrest-core-1.3.jar \
      org.junit.runner.JUnitCore io.snowcamp.papaya.test.TestSuite

