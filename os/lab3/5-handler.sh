#! /bin/bash

mode="sum"
result=1

(tail -f pipe) |
while :
do
    read line
    
    case "$line" in
        QUIT)
            echo "handler: exit"
            kill -USR1 $(cat ".pid")
            exit
            ;;
        "+")
            mode="sum"
            ;;
        '*')
            mode="multiply"
            ;;
        *)
            number_regexp='^[0-9]+$'
            if [[ $line =~ $number_regexp ]] 
            then
                if [[ "$mode" == "sum" ]] 
                then
                    let "result += $line"
                else
                    let "result *= $line"
                fi
            else
                echo "handler error: unknown token"
                kill -USR2 $(cat ".pid")
                exit
            fi
            ;;
    esac
    
    echo "res: $result"
done
