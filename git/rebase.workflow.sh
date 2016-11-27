#!/bin/bash

################################################################################
##                                                                            ##
## This script captures a sequence of steps to create a pull request          ##
## for a feature branch that has all intermediate commits squashed            ##
## into a single commit on a PR-specific branch.                              ##
##                                                                            ##
## It also incorporates a rebase onto the main development branch which       ##
## has presumably advanced while the feature has been under development.      ##
##                                                                            ##
################################################################################

## Assumptions and Definitions
##
## Name of branch where feature development has occured
##
export BRANCH=f/some-feature

## Update your local master branch to incoroprate all changes from remote
##
git checkout master
git pull

## Jump onto feature branch
## Then create a new branch off of that for your PR
##
git checkout ${BRANCH}
git checkout -b PR-${BRANCH}

## Look at the commit history to see how far back to branch.
## Make note of the HASH of the first commit
## Then rebase using SQUASH option for all but the first commit.
##
git log --oneline
git rebase  -i ${HASH}
git commit --amend -m "Feature implementation ..."

## Pushing your new branch to remote serves as a checkpoint so you don't
## have to redo all of the previous steps if you want to restart.
## The -u options sets this branch to have a tracking branch.
##
git push -u origin pr-${BRANCH}
git fetch
git rebase origin/master
git status

##
## FIX ANY ISSUES DUE TO PULLING IN LATEST CHANGES
##

## If you prefer only a single commit instance in the history
## rather than 2, you can finish with this "commit"
##
## git add .
## git commit --amend --no-edit
##
## otherwise, finish
##
git commit -a -m "Rebase PR onto origin/master & update feature"
git push -f 







