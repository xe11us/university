 #!/bin/bash
word="[[:alpha:]]{4,}"
man bash | grep -Eo "$word" | sort -n | uniq -c | sort -nr | head -3
