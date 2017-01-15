## Provide a short USAGE string and exit the program.
##
## This function expects a first argument which is the USAGE text. If it
## is not provided, a developer-error message is emitted.
##
## An optional second parameter acts as an error message that is printed
## before the usage text.
##
function usage()
{
	local COLOR_FAIL=$'\033[31;1m'
	local COLOR_STOP=$'\033[0m'
	local dev="Script developer error: No message provided to message usage() function."
	local msg="$1"
	local err="$2"

	if [ -n "$err" ] ; then
		printf "%sERROR:%s %s\n" "$COLOR_FAIL" "$COLOR_STOP" "$err"
	fi

	if [ -n "$msg" ] ;
	then
		echo $msg
	else
		echo $dev
	fi

	exit 0
}

