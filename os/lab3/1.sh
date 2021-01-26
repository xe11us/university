#! /bin/bash
cd ~
(mkdir test &&  echo "catalog test was created successfully" > "report" && cd test && touch $(date +%Y-%m-%d_%H:%M:%S)) > /dev/null 2>&1

ping -c 1 www.net_nikogo.ru > /dev/null 2>&1 || (echo "$(date +%Y-%m-%d_%H:%M:%S) host www.net_nikogo.ru doesn't respond" >> "report")
