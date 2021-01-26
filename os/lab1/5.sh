#!/bin/bash
journalctl | awk '$6=="<info>" { print }' > info.log
