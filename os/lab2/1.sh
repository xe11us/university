#!/bin/bash
list=$(ps ux)
A=$(echo "$list" | wc -l)
let "A -= 1"
echo "$A" > first.out
echo "$list" | awk 'NR>1{print ($2 ":" $11) }' >> first.out
