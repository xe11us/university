#! /bin/bash 
at -f "first.sh" now  > /dev/null 2>&1
tail -fn 0 "/home/alexey/report"

