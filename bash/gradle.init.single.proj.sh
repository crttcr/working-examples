#!/bin/bash

## Header text
##
echo ''
echo 'Initializing single project build with Gradle'
echo ''

here=$(pwd)

## Confirm proper usage on the command line
##
if [[ $# -ne 0 ]]; then
	echo "FAIL : No parameters are expected"
	echo "usage: $0"
	exit
fi


## Run Gradle Init
##
gradle init --type java-library

## Replace generated build.gradle with custom template
##
mv build.gradle old.build.gradle
cp ~/bin/.gradle_templates/single.build.gradle ./build.gradle

## Bring in starting .gitignore file
cp ~/bin/.gradle_templates/gitignore ./.gitignore

## Remove unwanted files from directory master
##
rm gradlew.bat

## Return to the starting directory
##
cd ${here}

echo 'Run this command to perform your first build:'
echo ''
echo "gradle build"
echo ''
echo "Done."

