#!/bin/bash

if [[ ! -d "$HOME/restore" ]]
then
  mkdir "$HOME/restore"
fi

latest_backup=$(find "$HOME" -maxdepth 1 -name "Backup-*" \
  | sort -t '-' -k 2  \
  | tail -1)

if [[ "$latest_backup" == "" ]]
then
  echo "Nothing to restore"
  exit 1
fi

for file in "$latest_backup/"*
do
  if [[ ! "$file" =~ \."[0-9]{4}-[0-9]{2}-[0-9]{2}"$ ]]
  then
    cp "$file" "$HOME/restore"
  fi
done
