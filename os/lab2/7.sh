#!/bin/bash

for Pid in `ps aux | awk 'NR>1{ print $2 }'`
do
    if [[ -f "/proc/$Pid/io" ]]
    then
        read=$(grep -Eo "read_bytes:[[:space:]][0-9]{1,}" "/proc/$Pid/io" | awk '{ print $2 }')
        echo "$Pid $read"
    fi
done > "before.out" #вывод вида Pid количество_прочитанных_байт_на_момент_запуска_программы

awk '{ print $1 }' "before.out" > "pid.list" #вывод всех pid в другой файл

sleep 5

for Pid in `cat "pid.list"`
do
    if [[ -f "/proc/$Pid/io" ]] && [[ -f "/proc/$Pid/cmdline" ]]
    then
        read_new=$(grep -Eo "read_bytes:[[:space:]][0-9]{1,}" "/proc/$Pid/io" | awk '{ print $2 }')
        read_old=$(grep -Eo "$^Pid [0-9]{1,}" "before.out" | awk '{ print $2 }')
        read=$(echo "$read_new $read_old" | awk '{ print $1 - $2 }')
        cmdline=$(tr -d '\0' < /proc/$Pid/cmdline)
        echo "$read $Pid $cmdline"
    fi
done | sort -nk 1 | awk 'NF==3{ print }' | tail -3 | awk '{ x = $1; y = $2; $1=$2=""; print y":"$0":"x }'
