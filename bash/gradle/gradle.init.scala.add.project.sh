#!/bin/bash

## Header text
##
echo ''
echo 'Initializing an incremental subproject with Gradle'
echo ''

here=$(pwd)
name=`basename $here`

## Confirm proper usage on the command line
##
if [[ $# -ne 0 ]]; then
	echo "FAIL : No parameters are expected. Make new directory first and cd there"
	echo "usage: $0"
	exit
fi

## Confirm that the current directory is empty
##
if [ "$(ls -A .)" ]; then
	echo "Directory  [$here] is not empty. Exiting"
	exit
else
	echo "Directory is empty. Proceeding."
fi

## Run Gradle Init
##
gradle init --type java-library
echo "Gradle Java project initialized."

## Remove Sample files
##
find . -name 'Library*.java' | xargs rm
echo "Gradle sample files removed."

## Create directories for Scala and resources
##
mkdir -p src/{main,test}/{scala,resources}
cd src
echo "Created scala directories"
tree
cd ..
sleep 3

## Remove settings.gradle because we're a subproject, not a master
##
rm settings.gradle
echo "Removed [settings.gradle] because this is a subproject."
sleep 1

## Replace generated build.gradle with custom template
##
rm build.gradle
cp ~/bin/.gradle_templates/sub.scala.build.gradle ./build.gradle
echo "Replace build.gradle with version supporting Scala."

## Bring in starting .gitignore file
cp ~/bin/.gradle_templates/gitignore ./.gitignore
echo "Brought in .gitignore file."

## Remove unwanted files from directory master
##
rm gradlew.bat

## Return to the starting directory
##
cd ${here}

echo ""
sleep 1
echo "TODO: Edit [build.gradle] to add dependencies: compile project(':other')" 
sleep 1
echo "TODO: Edit [build.gradle] of dependent projects: compile project(':$name')" 
sleep 1
echo 'TODO: Go to root directory and add this project to [settings.gradle]'
sleep 3

echo ''
echo 'Run this command to perform your first build:'
echo ''
echo "gradle cleanEclipse eclipse build"
echo ''
echo "Done."

