#!/bin/bash

## [Merge]
##
## Checkout the version of "file.a" from either
## the current branch (ours) or the branch being
## merged into the current branch (theirs)
##
git checkout --{ours,theirs} file.a
