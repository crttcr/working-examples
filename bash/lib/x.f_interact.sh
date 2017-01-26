#!/bin/bash

. f_interact.sh
. v_colors.sh

value=$(echo "" "cow" | promptForRequiredString "Enter a string: ")
if [ "$value" ne "cow" ]; then
	echo $value
	printf "%sFAIL%s: value [$value] was not the same as [cow].\n" "$COLOR_FAIL" "$COLOR_STOP"
	exit 1
fi

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

