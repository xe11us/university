#!/bin/bash 

for Pid in `ps aux | awk 'NR>1{ print $2 }'`
do  
    if [[ -f "/proc/$Pid/status" ]]
    then
        echo "Pid: $Pid `awk -F ":[[:space:]]" '$1 == "VmSize"{ print $2 }' "/proc/$Pid/status"`"
    fi
done | sort -nk 3 | tail -1

top -o VIRT | head -8 | tail -2
