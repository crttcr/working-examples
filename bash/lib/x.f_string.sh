#!/bin/bash

. v_colors.sh
. f_string.sh

template="This is a template. And [%s^token%s] is in the second sentence."

string=$(replaceTokenIfTrue "$template" "^token" true "WAS_TRUE" "WAS_FALSE")

printf "Expansion when true : $string\n" "$COLOR_GREEN" "$COLOR_RESET"

string=$(replaceTokenIfTrue "$template" ^token false "WAS_TRUE" "WAS_FALSE")

printf "Expansion when false: $string\n" "$COLOR_RED" "$COLOR_RESET"

string=$(replaceTokenIfTrue "$template" ^token false "WAS_TRUE")

printf "Expansion when false and no final parameter: $string\n" "$COLOR_RED" "$COLOR_RESET"
