#!/bin/bash

## See what's in the index
## 
##
git ls-files

## Remove a file from the index only
##
git rm --cached [file]

## Remove a file from the index AND the working tree
##
## If the staged content doesn't match the tip of the branch or the file on disk,
## you get a warning which can be ignored with --force
##
git rm [file]
