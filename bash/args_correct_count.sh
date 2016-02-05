#! /bin/bash

## Check for the correct number of arguments
##
## usage:
## bash args_correct_count.sh alpha bravo
##

if [ "$#" -ne 2 ]; then
   echo "FAIL: Expected 2 parameters. Received $#"
fi

