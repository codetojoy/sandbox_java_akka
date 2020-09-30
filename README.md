## Summary

* simple sandbox for Akka framework, based on the tutorial 
* Workers send info to a Calculator
    - for `a, b, c`, if `c == a + b`, then Calculator sends to Reporter
* uses JDK 15
    - `--enable-preview` is on for sealed interface

### To Run

* execute: `./run.sh`
    - after initial log flurry, press Enter as the app is waiting for input
