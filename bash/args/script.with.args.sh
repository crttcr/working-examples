#!/bin/bash

## Note, this test script can be called with or without arguments.
## If arguments are provided, those are presented to process_args,
## otherwise a hard-coded set are provided
##

LIBDIR=~/bin/lib
SRCDIR="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

. $LIBDIR/v_colors.sh
. $LIBDIR/f_usage.sh
. $SRCDIR/.$(basename $0).args

if [ $# -eq 0 ]; then
	args=("-f $0 -t some.topic a b c")
else
	args=("$*")
fi

process_args $args

printf "File from args     : [%s%s%s]\n" "$COLOR_BLUE"  "$FILE"   "$COLOR_RESET"
printf "Topic from args    : [%s%s%s]\n" "$COLOR_BLUE"  "$TOPIC"  "$COLOR_RESET"
printf "Socket from default: [%s%s%s]\n" "$COLOR_GREEN" "$SOCKET" "$COLOR_RESET"
