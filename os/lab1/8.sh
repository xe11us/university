#!/bin/bash
sort "/etc/passwd" -k 3 -t ":" -n | awk -F ":" '{ print $1":"$3 }'
