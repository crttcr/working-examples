#!/bin/bash

NC='\033[0m'
RED='\033[0;31m'
BLUE='\033[0;34m'
GREEN='\033[0;32m'
PURPLE='\033[0;35m'

## Header text
##
echo ''
echo 'Initializing multi project build with Gradle'
echo ''

here=$(pwd)

## Confirm proper usage on the command line
##
if [[ $# -lt 3 ]]; then
	echo "FAIL : At least 3 parameters are needed. Master project and 2 subs"
	echo "usage: $0 project_master sub_a sub_b ..."
	exit
fi


## Process the master directory
##
master=$1
shift 1

echo "Create project master directory: ${master}"

## Create directory
##
mkdir ${master}
cd ${master}

## Run Gradle Init
##
gradle init --type java-library

## Replace generated build.gradle with custom template
##
mv build.gradle old.build.gradle
cp ~/bin/.gradle_templates/master.build.gradle ./build.gradle

## Remove unwanted files from directory master
##
rm gradlew.bat

## Add subprojects to settings.gradle
##
for arg in "$@"
do
   echo "include '${arg}'" >> settings.gradle
done

## Bring in starting .gitignore file
##
cp ~/bin/.gradle_templates/gitignore ./.gitignore


##
## Handle all of the sub directories
##

for arg in "$@"
do
	printf "Create subproject ${PURPLE}${arg}${NC}.\n"
	front=${arg%%:*}
	scala=${arg#*:}

	## printf "Split Values: ${front} and ${scala}\n"
	mkdir ${front}
	cp -R src ${front}

	## Test if ${scala} is empty and copy the appropriate build file.
	##
	if [ ${#front} == ${#arg} ]; then
		printf "Copy ${GREEN}java${NC} build.gradle into [$front]\n"
		cp ~/bin/.gradle_templates/sub.build.gradle ${front}/build.gradle
	else   
		printf "Copy ${BLUE}scala${NC} build.gradle into [$front]\n"
		rm    ${front}/src/main/java/*
		rm    ${front}/src/test/java/*
		rmdir ${front}/src/main/java
		rmdir ${front}/src/test/java
		mkdir ${front}/src/main/scala
		mkdir ${front}/src/test/scala
		cp ~/bin/.gradle_templates/sub.scala.build.gradle ${front}/build.gradle
	fi
	unset scala
done

## Remove src directory from master
##
rm -rf src

## Return to the starting directory
##
cd ${here}

## Reminder about dependencies
##
echo << EOT

Remember to define project dependencies among subprojects

## project(':program')
## {
##    dependencies
##    {
##       compile project(':domain')
##    }
## }

EOT

echo 'Run this command to perform your first build:'
echo ''
echo "cd ${master} ; gradle build"
echo ''
echo "Done."

