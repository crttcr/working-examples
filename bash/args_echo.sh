#! /bin/bash

## Echo the program arguments
##
## usage:
## bash args_echo.sh alpha
##

## Echo the arguments, one per line
##
for arg in "$@"; do
     echo "$arg"
done
