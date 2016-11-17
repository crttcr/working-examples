#!/bin/bash

## This script captures a sequence of steps to create a pull request
## for a feature branch that has all intermediate commits squashed
## into a single commit on a PR-specific branch.
##
## It also performs a rebase based on the main development branch which
## has presumably advanced while the feature has been under development.
##

## Assumptions and Definitions
##
## Your feature branch where development has occured
##
export BRANCH=feature/inspection-extract

## Update your local develop branch to incoroprate all changes from remote
##
git checkout develop
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
git push -u origin PR-${BRANCH}
git fetch
git rebase origin/develop
git status
git push -f 

## NOW FIX ANY COMPILE ISSUES DUE TO PULLING IN LATEST CHANGES
git add .
git commit -m "Rebase PR onto origin/develop & update feature"
git push







