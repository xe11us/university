#! /bin/bash

function existence_check {
    if [[ !(-f "$2") ]]
    then
        echo "can't $1 $2 : no such file"
        exit
    fi
}

name=$1

OIFS=$IFS
IFS=$'\n'

for line in $(grep -E "$name" "$HOME/.trash.log")
do
    echo "$line" | awk ' -F " " {print "Can restore file: " $1}'
    answer="start"
    while [[ !($answer == "y" || $answer == "n") ]]
    do
        echo "Do you want to do it? y / n"
        read answer
        case $answer in
            "y") 
                trash_id=$(echo "$line" | awk ' -F " " {print $2}')
                link_path=$(echo "$HOME/.trash/$trash_id")
                existence_check "restore file" "$link_path"
                
                path=$(echo "$line" | awk ' -F " " {print $1}')
                let "len = ${#path} - ${#name}"
                dir=$(echo "$path" | cut -c 1-"$len")
                
                if [[ !(-d $dir) ]]
                then
                    echo "This directory path now is incorrect: $dir. File will be saved in $HOME."
                    dir=$(echo "$HOME/")
                fi
                
                name_is_correct=0
                while [[ name_is_correct -eq 0 ]]
                do
                    path="$dir$name"
                    
                    if [[ !(-f $path) ]]
                    then
                        name_is_correct=1
                        ln "$link_path" "$name"
                    else
                        echo "File with this name already exists. You should rename it:"
                        read name
                    fi
                done
                
                existence_check "delete file from trash" "$link_path"
                rm "$link_path"
                sed -i "\|$trash_id|d" "$HOME/.trash.log"
            
                echo "Done!"
                
                exit
                ;;
            "n")
                ;;
            *)
                echo "Incorrect token. Please, try again"
                ;;
        esac
    done
done

echo "There are no files with this name left"
IFS=$OIFS
