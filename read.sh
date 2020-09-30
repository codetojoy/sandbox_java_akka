#!/bin/bash 

set -e 

LOG_FILE=out.log

stat $LOG_FILE > /dev/null 2>&1

echo "count of log lines:"
wc -l $LOG_FILE
echo ""

echo "elapsed:"
grep -i elapsed $LOG_FILE
echo ""

echo "count Reporter received:"
grep -i "tracer.*reporter.*received" $LOG_FILE | wc -l 

echo "grep STOPPED"
grep -i "tracer.*stopped" out.log 
