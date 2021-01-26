#! /bin/bash
echo $$ > ".pid"
 
usr1()
{
    echo "generator: QUIT command"
    echo "" > ".pid"
    exit
}
 
usr2()
{
    echo "generator error: unknown token"
    echo "" > ".pid"
    exit
}
 
trap 'usr1' USR1
trap 'usr2' USR2
 
while :
do
    read line
    echo "$line" > pipe
done


