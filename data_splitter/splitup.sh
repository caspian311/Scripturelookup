#!/bin/bash

current_file=1
count=1
limit_per_file=100

while read line; do
  echo "$line" >> "data$current_file.txt"
  count=$(( $count + 1 ))
  let switcher=$count%$limit_per_file
  if [ $switcher -eq 0 ]; then
    current_file=$(( $current_file + 1 ))
  fi 
done
