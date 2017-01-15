#!/bin/bash

. f_env.sh


## NOTE, calling process_environment without an argument
## or with an empty argument will result in an error.
##
## process_environment 
## process_environment ""
##

process_environment 'dev'

echo "__ZOOKEEPER=${__ZOOKEEPER}"
