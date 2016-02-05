#! /bin/bash

## Check that none of the arguments provided are empty
## (after checking there are some args in the first place)
##
## usage:
## bash args_are_not_empty.sh alpha bravo "" gamma
##

if [ "$#" -eq 0 ]; then
   echo "FAIL: Expected some parameters. Received none"
fi


for arg in "$@"; do
   if [[ -z "$arg" ]]; then
      echo "FAIL: Parameter undefined or empty."
      exit 0
   fi
done


