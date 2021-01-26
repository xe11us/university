#! /bin/bash

echo $$ > ".pid"

result=1
number=2

usr1()
{
    let "result += number"
    echo "result: $result"
}

usr2()
{
    let "result *= number"
    echo "result: $result"
}

sigterm()
{
    echo "handler: terminated by generator"
    exit
}

changenumber()
{
    if (( $number == 2 ))
    then
        number=5
    else
        number=2
    fi
}

subtract()
{
    let "result -= number"
    echo "result: $result"
}

trap 'usr1' USR1
trap 'usr2' USR2
trap 'sigterm' SIGTERM
trap 'changenumber' SIGQUIT
trap 'subtract' SIGPWR
while :
do
    A=1
done

echo "" > ".pid"
 
