#! /bin/bash

echo $$ > ".pid"

result=1

usr1()
{
    let "result += 2"
    echo "result: $result"
}

usr2()
{
    let "result *= 2"
    echo "result: $result"
}

sigterm()
{
    echo "handler: terminated by generator"
    exit
}

trap 'usr1' USR1
trap 'usr2' USR2
trap 'sigterm' SIGTERM

while :
do
    A=1
done

echo "" > ".pid"
