#! /bin/bash

## Test whether or not a variable is set and has a value
##
## usage:
## bash variable_has_value.sh 
##


PURPLE='\033[0;35m'
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'
var="something"
empty=""


if [ -z "$var" ]; then
	printf "${RED}ERROR${NC}: 'var' should have value [${PURPLE}${val}${NC}].\n"
else
	printf "${GREEN}SUCCESS${NC}: 'var' has value [${PURPLE}$var${NC}]. "
	printf "This is correct\n"
fi

if [ -z "$empty" ]; then
	printf "${GREEN}SUCCESS${NC}: 'empty' tests as having no value. "
	printf "This is correct\n"
else
	printf "${RED}ERROR${NC} 'empty' has value $empty\n"
fi
	
