#! /bin/bash 

function existence_check {
    if [[ !(-f "$2") ]]
    then
        echo "can't $1 $2 : no such file"
        exit
    fi
}

if [[ !(-d "$HOME/.trash") ]]
then
    mkdir "$HOME/.trash"
fi

name=$(date +"%F-%T:%N")

current_dir=$(pwd)

existence_check "create link to" "$current_dir/$1"

ln "$current_dir/$1" "$HOME/.trash/$name"

existence_check "delete" "$current_dir/$1"

rm "$current_dir/$1"

echo "$current_dir/$1 $name" >> "$HOME/.trash.log"
