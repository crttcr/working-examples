#! /bin/bash

## Check that the single argument provided is an existing directory
##
## usage:
## bash arg_is_directory.sh alpha
##

## Check arg count
##
if [ "$#" -ne 1 ]; then
   echo "FAIL: Expected 1 parameter. Received $0"
   exit 0
fi


## Fail if the arg is not an existing directory
##
if [[ ! -d "$1" ]]; then
   echo "FAIL: Parameter [$1] is not an existing directory"
   exit 0
fi
