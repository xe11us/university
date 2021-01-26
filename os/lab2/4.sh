#!/bin/bash

for Pid in `ps aux | awk 'NR>1{print $2}'`
do
    if [[ -f "/proc/$Pid/status" ]] && [[ -f "/proc/$Pid/sched" ]]
    then
    PPid=$(cat "/proc/$Pid/status" | grep -Eo "PPid:[[:space:]][0-9]{1,}" | awk '{ print $2 }')
    
    sum=$(cat "/proc/$Pid/sched" | grep -Eo "se.sum_exec_runtime[[:space:]]{1,}:[[:space:]]{1,}[0-9.]{1,}" | awk '{ print $3 }')
    switches=$(cat "/proc/$Pid/sched" | grep -Eo "nr_switches[[:space:]]{1,}:[[:space:]]{1,}[0-9]{1,}" | awk '{ print $3}')
    ART=$(echo "$sum $switches" | awk '{ print $1 / $2}')
    
    echo "ProcessID=$Pid : Parent_ProcessID=$PPid : Average_Running_Time=$ART"
    fi
done | sort -t "=" -nk 3 > fourth.out
