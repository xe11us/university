#!/bin/bash

SOURCE="$HOME/source"
DATE=$(date +%F)

report() {
    echo "$(date +'%Y-%m-%d-%H:%M:%S'): $*" >> "$HOME/backup-report"
}

copy() {
  cp "$1" "$2" && report "   $(basename "$1") has been copied into $2"
}

backup_dir=""

for dir in ~/Backup-*
do
  backup_date=$(echo "$dir" | awk -F "Backup-" '{ print $2 }')
  if [[ $backup_date > $(date -d 'now -7 days' +%F) ]]
  then
    backup_dir="$dir"
  fi
done

if [[ "$backup_dir" == "" ]]
then
  backup_dir="$HOME/Backup-$DATE"
  mkdir "$backup_dir"
  report "Directory $backup_dir has been created"
  
  for file in "$SOURCE"/*
  do
    copy "$file" "$backup_dir"
  done
else
  for file in "$SOURCE"/*
  do
    backup_file="$backup_dir/$(basename "$file")"
    if [[ -f "$backup_file" ]]
    then
      size=$(stat -c %s "$file")
      backup_size=$(stat -c %s "$backup_file")

      if ((size != backup_size ))
      then
        mv "$backup_file" "${backup_file}.$DATE" 
        report "   Previous file $backup_file was renamed to $(basename $file).$DATE"
      else
        continue
      fi
    fi
    copy "$file" "$backup_dir"
  done
fi
