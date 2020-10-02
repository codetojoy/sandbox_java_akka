## Summary

* simple sandbox for Akka framework, based on the tutorial 
* Workers send info to a Calculator
    - for `a, b, c`, if `c == a + b`, then Calculator sends to Reporter
* uses JDK 15
    - `--enable-preview` is on for sealed interface

### To Run

* execute: `./run.sh`
    - after initial log flurry, press Enter as the app is waiting for input

### TODO

* based on IoT tutorial example, I couldn't get the unit tests to pass
    - see links in WorkerTest.java
* some pretty hairy generic/type-token usage... I may have interpreted it incorrectly 
