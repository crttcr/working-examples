#!/bin/bash

#######################################################################################
##                                                                                   ##
## Consume the contents of a topic and display the results on STDOUT.                ##
##                                                                                   ##
##                                                                                   ##
#######################################################################################

SRCDIR="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
LIBDIR=~/bin/lib
. $LIBDIR/v_colors.sh
. $LIBDIR/f_env.sh
. $LIBDIR/f_usage.sh
. $LIBDIR/f_interact.sh
. $SRCDIR/.$(basename $0).args
unset SRCDIR
unset LIBDIR


process_args $*
process_environment $ENV


##########################################
## Provide summary of operation         ##
## giving the user a chance to cancel.  ##
##########################################

echo "Reading Topic       : [$TOPIC]"
case $ENV in 
	dev)
		printf "Environment         : [%s$ENV%s]\n" "$COLOR_GREEN" "$COLOR_RESET"
		;;
	qa)
		printf "Environment         : [%s$ENV%s]\n" "$COLOR_BLUE" "$COLOR_RESET"
		;;
	stage)
		printf "Environment         : [%s$ENV%s]\n" "$COLOR_PURPLE_BOLD" "$COLOR_RESET"
		;;
	prod)
		printf "Environment         : [%s$ENV%s]\n" "$COLOR_RED_BOLD" "$COLOR_RESET"
		;;
esac

echo "Rewind offset to 0  : [$REWIND]"
echo "Include timestamp   : [$SHOW_TIMESTAMP]"
echo "Display key         : [$SHOW_KEY]"

echo
confirmOrExit "Press 'y' or 'Y' to consume topic, any other key to abort: "
echo

cmd="kafka-console-consumer.sh ^rewind ^timestamp ^printkey \
	--topic $TOPIC --zookeeper $__ZOOKEEPER                  \
   --property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer"

if [ "$REWIND" = true ]; then
	cmd=${cmd/^rewind/--from-beginning}
else
	cmd=${cmd/^rewind/}
fi
if [ "$SHOW_KEY" = true ]; then
	cmd=${cmd/^printkey/--propery print.key=true}
else
	cmd=${cmd/^printkey/}
fi
if [ "$SHOW_TIMESTAMP" = true ]; then
	cmd=${cmd/^timestamp/--propery print.timestamp=true}
else
	cmd=${cmd/^timestamp/}
fi

echo "[------Command text----------]"
echo $cmd
echo "[----------------------------]"

$cmd

echo "Done."

