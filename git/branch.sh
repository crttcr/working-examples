#!/bin/bash

## Switch to the previous branch
##
git branch -

## List upstream branches
##
git branch -vv

## Rename a local branch
##
git branch -m oldname newname

## Rename a remote branch means pushing the
## newly named branch and then deleting the old
##
git push -u newname
git push origin :oldname

