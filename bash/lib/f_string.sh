## Functions for manipulating strings
## 


## Replaces a token within a template based on whether or not a boolean
## value is true.

function replaceTokenIfTrue()
{
	local __template="$1"
	local __token="$2"
	local __bool=$3
	local __ifTrue="$4"
	local __ifFalse="$5"

	if [ $__bool = true ]; then
		__result=${__template/$__token/$__ifTrue}
	else
		__result=${__template/$__token/$__ifFalse}
	fi

	echo "$__result"
}
