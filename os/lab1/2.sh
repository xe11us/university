#!/bin/bash
while [[ -z $S  ||  $S != "q" ]]
do
    read S
    Ans="$Ans$S"
done

echo $Ans
