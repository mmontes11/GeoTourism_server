#!/bin/sh

#PATH
PATH="/usr/lib/jvm/java-8-oracle/bin:/usr/local/java/jdk/bin:/usr/local/bin:/usr/bin:/bin:/usr/local/games:/usr/games"
export PATH

#JAVA
JAVA_HOME="/usr/lib/jvm/java-8-oracle"
export JAVA_HOME
export JAVA_CMD=$JAVA_HOME/bin/java
export PATH=$JAVA_HOME/bin:$PATH

# PENTAHO
export PENTAHO_HOME=/opt/kettle

#CLASSPATH
CLASSPATH=.
for i in $JAVA_HOME/jre/lib/*jar ; do
  CLASSPATH=$CLASSPATH:$i
done
CLASSPATH=$CLASSPATH:$PENTAHO_HOME/lib/postgresql-9.3-1102-jdbc4.jar
CLASSPATH=$CLASSPATH:$PENTAHO_HOME/plugins/steps/weka-scoring/lib/weka.jar
CLASSPATH=$CLASSPATH:$PENTAHO_HOME/plugins/steps/weka-scoring/lib/XMeans.jar
export CLASSPATH

/opt/kettle/kitchen.sh -file=./ETL/index.kjb -logfile=./ETL/log_ETL.log
