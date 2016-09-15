#! /bin/bash

## Split a variable into an array
##
## usage:
## bash variable_split.sh alpha:bravo
##

if [ "$#" -ne 1 ]; then
   echo "FAIL: Expected 1 parameter. Received $#"
fi

## Using bash parameter expansion to chop the input
##
## INPUT="a:b:C"

ONE=${1%%:*}
TWO=${1#*:}

echo "One is : $ONE"
echo "Two is : $TWO"

