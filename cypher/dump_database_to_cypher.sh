#!/bin/bash

## This is a simple script to dump a Neo4j database to a file containing
## the Cypher commands to recreate it.
##
## Most of the space is taken up by comments and variable declarations
##

NEO_DIR=/Users/reid.dev/Desktop/Neo/neo4j-community-2.3.3
NEO_BIN=${NEO_DIR}/bin
NEO_SHELL=neo4j-shell
TIMESTAMP=`date +"%Y%m%d_%H%M%S"`
FILE=neo4j.database.dump.$TIMESTAMP.cypher

## The dump command
##
${NEO_BIN}/${NEO_SHELL} -c dump > $FILE


## NOTE: To read this file back into Neo4j, it's simply this:
##
## cat $FILE | ${NEO_BIN}/${NEO_SHELL}
