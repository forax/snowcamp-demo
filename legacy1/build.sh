#!/bin/bash
export JAVA_HOME=/usr/jdk/jdk1.8.0_65/
#export JAVA_HOME=/usr/jdk/jdk-9/
export java=$JAVA_HOME/bin/java
export javac=$JAVA_HOME/bin/javac

mkdir target
$javac -classpath deps/vertx-core-3.3.3.jar:deps/vertx-web-3.3.3.jar \
       -d target/ \
       $(find src/main/java/ -name "*.java")

$java -classpath target:deps/vertx-core-3.3.3.jar:deps/vertx-web-3.3.3.jar:deps/netty-transport-4.1.5.Final.jar:deps/netty-common-4.1.5.Final.jar:deps/netty-resolver-4.1.5.Final.jar:deps/netty-resolver-dns-4.1.5.Final.jar:deps/netty-codec-http-4.1.5.Final.jar:deps/netty-codec-4.1.5.Final.jar:deps/netty-buffer-4.1.5.Final.jar:deps/netty-handler-4.1.5.Final.jar:deps/netty-codec-dns-4.1.5.Final.jar:deps/netty-codec-http2-4.1.5.Final.jar:deps/jackson-databind-2.7.4.jar:deps/jackson-core-2.7.4.jar:deps/jackson-annotations-2.7.0.jar \
      io.snowcamp.vertx.ExampleApp


