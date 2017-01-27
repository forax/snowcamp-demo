#!/bin/bash
export JAVA9_HOME=/usr/jdk/jdk-9
export java=$JAVA9_HOME/bin/java

export PRO_HOME=/usr/jdk/pro

echo ""
echo "============================"
echo "= Building with pro        ="
echo "============================"
echo ""
$PRO_HOME/bin/pro
echo ""
echo "======================"
echo "= End of Build       ="
echo "======================"
echo ""

#$java --module-path target/test/artifact:target/main/artifact:deps  \
#      -m papaya/io.snowcamp.papaya.test.Run
