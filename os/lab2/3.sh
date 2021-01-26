#!/bin/bash
ps aux | sort -k 9 | tail -9 | head -1 | awk '{ print $2 }'
