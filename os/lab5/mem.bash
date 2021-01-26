#!/bin/bash
 
 echo "" > "report.log"
 
 array=()
 cnt=0
 
 while :
 do
  let "cnt++"
  
  array+=(1 2 3 4 5 6 7 8 9 10)
  
  if (( cnt % 100000 == 0 ))
  then
    echo "${#array[@]}" >> "report.log"
  fi
 done
