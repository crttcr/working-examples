#!/bin/bash

########################################################
##                                                    ##
##               Configuration examples               ##
##                                                    ##
##                                                    ##
## Note, it is possible to review and change config   ##
## values directly by editing the file                ##
##                                                    ##
## ~/.gitconfig                                       ##
##                                                    ##
##                                                    ##
########################################################

## Basic config for new Git installations
##
git config --global user.name "Wyatt Earp"
git config --global user.email developer@example.com


## Use color for tty connections
##
git config --global color.ui auto

## Abbreviate Object Identifiers
##
git config --global core.abbrev 8
git config --global log.abbrevCommit yes

## Choose Git's editor
##
git config --global core.editor vi

