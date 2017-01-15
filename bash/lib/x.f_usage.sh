#!/bin/bash

. f_usage.sh

USAGE="$0 [-h] [-f file]"

## Note, it is important to wrap the usage variable access in quotes so that
## it is presented as a single parameter to the function.
##


usage "$USAGE" "A tragic if-then-else malfunction occurred."
usage "$USAGE"

