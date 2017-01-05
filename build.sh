#!/bin/bash
export JAVA8_HOME=/usr/jdk/jdk1.8.0_65
export JAVA9_HOME=/usr/jdk/jdk-9
export java=$JAVA9_HOME/bin/java
export javac=$JAVA8_HOME/bin/javac

mkdir -p target
$javac -classpath "deps/*" \
       -d target/ \
       $(find src/main/java/ -name "*.java")
$javac -classpath target:deps/json-20160810.jar:deps/junit-4.12.jar \
       -d target/ \
       $(find src/test/java/ -name "*.java")

$java -classpath target:deps/vertx-core-3.3.3.jar:deps/vertx-web-3.3.3.jar:deps/netty-transport-4.1.5.Final.jar:deps/netty-common-4.1.5.Final.jar:deps/netty-resolver-4.1.5.Final.jar:deps/netty-resolver-dns-4.1.5.Final.jar:deps/netty-codec-http-4.1.5.Final.jar:deps/netty-codec-4.1.5.Final.jar:deps/netty-buffer-4.1.5.Final.jar:deps/netty-handler-4.1.5.Final.jar:deps/netty-codec-dns-4.1.5.Final.jar:deps/netty-codec-http2-4.1.5.Final.jar:deps/jackson-databind-2.7.4.jar:deps/jackson-core-2.7.4.jar:deps/jackson-annotations-2.7.0.jar \
      --add-modules java.xml.bind   -Dsun.reflect.debugModuleAccessChecks=true \
      io.snowcamp.papaya.web.ExampleApp

#$java -classpath target:deps/json-20160810.jar:deps/junit-4.12.jar:deps/hamcrest-core-1.3.jar \
#      org.junit.runner.JUnitCore io.snowcamp.papaya.test.TestSuite
