#!/bin/bash
path="/var/log/Xorg.0.log"

awk '$3=="(WW)" { $3="Warning"; print }' "$path" > full.log
awk '$3=="(II)" { $3="Information:"; print }' "$path" >> full.log
cat full.log
