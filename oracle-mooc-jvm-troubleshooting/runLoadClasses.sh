#!/usr/bin/env bash

java -XX:+PrintGCDetails -XX:+TraceClassUnloading -XX:+TraceClassLoading \
     -XX:MaxMetaspaceSize=20m -XX:-UseCompressedOops \
     -cp lib:out/production/oracle-mooc-jvm-troubleshooting \
     oracle.mooc.jvm.troubleshooting.week2.LoadClasses
