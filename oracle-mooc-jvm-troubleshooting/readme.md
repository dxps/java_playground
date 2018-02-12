## About

This project contains materials used during [Oracle MOOC: Java Virtual Machine Troubleshooting (2018)](https://apexapps.oracle.com/pls/apex/f?p=44785:149:0::::P149_EVENT_ID:5688) training.

### Run


#### Week 2

Run `LoadClasses` using the provided `runLoadClasses.sh` script or using:
```bash
java -XX:+PrintGCDetails -XX:+TraceClassUnloading -XX:+TraceClassLoading \
     -XX:MaxMetaspaceSize=20m -XX:-UseCompressedOops \
     -cp lib:out/production/oracle-mooc-jvm-troubleshooting \
     oracle.mooc.jvm.troubleshooting.week2.LoadClasses
```
