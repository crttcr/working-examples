#!/bin/bash

## Compare local repo to origin/HEAD
##
git diff @{upstream}

## Compare the index to the last commit.
## (Examine what is staged)
##
## --staged and --cached are equivalent in this context
##
git diff --staged
git diff --cached
