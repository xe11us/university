#! /bin/bash
echo -n > "pid"

for i in {1..3}
do
    nice bash -c 'echo $$ >> "pid"; while :; do A=7; done ' &
done

A=$(cat "pid" | head -1)
B=$(cat "pid" | head -2 | tail -1)
C=$(cat "pid" | head -3 | tail -1)

for i in $A $B $C
do
    echo $i
done

cpulimit -p $A -l 10 -z &
