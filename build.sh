#!/bin/bash
export JAVA8_HOME=/usr/jdk/jdk-8
export JAVA9_HOME=/usr/jdk/jdk-9
export java=$JAVA9_HOME/bin/java
export javac=$JAVA8_HOME/bin/javac

# Compile
#   change this in part 2
mkdir -p target && rm -rf target/*
cp -r src/main/resources/* target/
$javac -classpath deps/json-20160810.jar:deps/vertx-core-3.3.3.jar:deps/vertx-web-3.3.3.jar \
       -XDignore.symbol.file \
       -d target/ \
       $(find src/main/java/ -name "*.java")
$javac -classpath target:deps/json-20160810.jar:deps/junit-4.12.jar \
       -d target/ \
       $(find src/test/java/ -name "*.java")

# Run
#   change this in part 1
$java -classpath target:deps/vertx-core-3.3.3.jar:deps/vertx-web-3.3.3.jar:deps/vertx-internal-3.3.3.jar \
      io.snowcamp.papaya.web.ExampleApp

#$java -classpath target:deps/json-20160810.jar:deps/junit-4.12.jar:deps/hamcrest-core-1.3.jar \
#      org.junit.runner.JUnitCore io.snowcamp.papaya.test.TestSuite

