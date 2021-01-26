#! /bin/bash
 
while :
do
    read line
    case "$line" in
    "+")
        kill -USR1 $(cat ".pid")
        ;;
    "*")
        kill -USR2 $(cat ".pid")
        ;;
    *"TERM"*)
        kill -s SIGTERM $(cat ".pid")
        exit
        ;;
    esac
done
