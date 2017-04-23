#!/bin/bash

## This workflow can be used to create a set of logically
## coherent commits when you have a bunch of changes in your
## working tree that cross good commit boundaries.
##

## Step 1.
##
## Add some of the changed items to your index
##
git add [... use some form of add to pick items]

## Step 2.
##
## Save  all of the working tree changes that are not staged
## in the stash and remove them from the working tree, keep
## staged changes in the working tree.
##
git stash --keep-index

## Step 3.
##
## Examine your working tree to ensure that the changes in
## the index make a correct verision of your code
##
mvn install
gradle test

## Step 4.
## 
## Commit the changes
##
git commit 


## Step 5.
## 
## Restore uncommitted changes to the working tree, and
## repeat as necessary.
##
git stash pop
