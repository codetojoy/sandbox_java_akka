#!/bin/bash 

set -e 

./gradlew -q clean cJ

LOG_FILE=out.log
rm -f $LOG_FILE

./gradlew -q run | tee $LOG_FILE 


