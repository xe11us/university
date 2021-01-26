#!/bin/bash
if [[ "$PWD" == "$HOME" ]]
    then echo "$HOME"
    echo 0
    exit 0
else
    echo "Not a home directory"
    echo 1
    exit 1
fi
