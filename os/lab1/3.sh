#!/bin/bash
echo "1: open nano"
echo "2: open vi"
echo "3: open links"
echo "4: close the menu"

read A

case $A in
    "1")
    nano
    ;;
    "2")
    vi
    ;;
    "3")
    links
    ;;
    "4")
    echo "goodbye"
    exit
    ;;
esac
