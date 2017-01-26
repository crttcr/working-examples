## Ask a user to confirm an action or halt the program.
## 
## Confirmation should be provided in the form of a 'y' or 'Y' character.
##
##
## The function takes an optional parameter which will be used as the text
## for prompting the user. Otherwise a default 'Are you sure? ' will be used.
##


function confirmOrExit()
{
	local COLOR_FAIL=$'\033[31;1m'
	local COLOR_STOP=$'\033[0m'
	local prompt='Are you sure? '
	
	if [ -n "$1" ]; 
	then
		prompt="$1"
	fi

	local ANSWER

	read -p "$prompt" -n 1 -r ANSWER
	echo ""
	
	if [[ $ANSWER =~ ^[yY]$ ]]
	then
		echo "Confirmation received."
	else
		printf "%sABORT.%s\n" "$COLOR_FAIL" "$COLOR_STOP"
		exit 0
	fi
}

function promptForRequiredString()
{
	local prompt=$1

	if [ -z "$prompt" ]; then
		prompt="Enter a string value: "
	fi

	unset __REPLY
	until [ -n "$__REPLY" ]; do
		read -p "$prompt" __REPLY
	done

	echo "$__REPLY"
}
	
