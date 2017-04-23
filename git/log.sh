#!/bin/bash

## See the RefLog
## 
## The reflog is the history operations for the current branch
## This is useful to see an ObjectId which is no longer 
## reachable from the commit graph.
##
git log -g

## Ascii-art representation of the commit graph
##
git log --oneline --graph

## Show branches in log output
##
git log --oneline --decorate

## [Merge]
## 
## See diffs of files with conflicts
##
git log -p --merge

