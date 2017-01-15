#!/bin/bash

#######################################################################################
##                                                                                   ##
## Consume the contents of a topic and display the results on STDOUT.                ##
##                                                                                   ##
##                                                                                   ##
##                                                                                   ##
##                                                                                   ##
#######################################################################################

LIBDIR=~/bin/lib
. $LIBDIR/v_colors.sh
. $LIBDIR/f_env.sh
. $LIBDIR/f_usage.sh
. $LIBDIR/f_interact.sh
unset LIBDIR

####################################
## Default Values                 ##
####################################

ENV=dev
SHOW_KEY=true
SHOW_TIMESTAMP=false


