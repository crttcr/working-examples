#!/bin/bash

. f_interact.sh
. v_colors.sh


echo "Answering 'Y' to function call with no parameters"
printf "%sNOTE%s: Prompt is only displayed when input comes from a terminal." "$COLOR_PURPLE_BOLD" "$COLOR_STOP"
echo "Y" | confirmOrExit

echo ""
echo "Answering 'y' to function call with optional second parameter."
p1="Do you believe in magic?"
printf "$p1"
echo "y" | confirmOrExit "$p1"

echo ""
p2="Are you insane? "
echo "Answering 'n' to function call with optional second parameter."
printf "$p2"
echo "n" | confirmOrExit "$p2"





