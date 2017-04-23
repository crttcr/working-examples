#!/bin/bash

## Update last commit with changes that are in the index
## and reuse the last commit message
##
git commit --amend -C HEAD
