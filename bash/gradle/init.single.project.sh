#!/bin/bash

here=$(pwd)

LIBDIR=~/bin/lib
SRCDIR="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. $LIBDIR/v_colors.sh
. $LIBDIR/f_usage.sh
. $LIBDIR/f_interact.sh

GRADLE_SCRIPT_DIR=~/bin/gradle
. $GRADLE_SCRIPT_DIR/definitions.bash

## Make sure the user does not accidentally create in 
## the wrong directory.
##
printf "\nStarting [%b$0%b]\n" "$BLUE" "$NC"
printf "New project files and directory structure will in starting directory.\n"
printf "Currently: [%b$here%b]\n" "$BLUE" "$NC"
confirmOrDie

## These two lines can be used to step through each line
##
## set -x
## trap read debug

LANGUAGE_MODE=undefined

if [ $# -eq 0 ]; then
	printf "What language will you use for this project? "
	printf "Choices ${BLUE}scala${NC}, ${BLUE}java${NC}, ${BLUE}mixed${NC}? [${PURPLE}mixed${NC}] "
	read -s -n 1 response
	if [ -z "$response" ] ; then
		response=m
	fi
	setMode $response
	echo "Response was $response"
else
	while getopts "sjm" arg; do
		case $arg in
		[sjm])
			setMode $arg 
			;;
		*)
			usage
			## strength=$OPTARG
			;;
		esac
	done
fi

printf "Initializing a single ${PURPLE}$LANGUAGE_MODE${NC} project.\n"

## Run Gradle Init
##
gradle init --type java-library

## Replace generated build.gradle with custom template
##
mv build.gradle build.gradle.original
BUILD_TEMPLATE=${GRADLE_SCRIPT_DIR}/templates/build.gradle.single
SED_FILE=${GRADLE_SCRIPT_DIR}/templates/build.gradle.single.${LANGUAGE_MODE}.sed

sed -f $SED_FILE < $BUILD_TEMPLATE > build.gradle

## Copy common Gradle files
##
cp ${GRADLE_SCRIPT_DIR}/templates/test-output.gradle gradle
cp ${GRADLE_SCRIPT_DIR}/templates/dependency.explore.gradle gradle

## Bring in starting .gitignore file
##
cp ${GRADLE_SCRIPT_DIR}/templates/dot_gitignore ./.gitignore

## Remove unwanted files from directory master
##
rm gradlew.bat

## Return to the starting directory and perform more initialization work
##
cd ${here}
sed -e "s#@PROJECT@#${here}#" <$GRADLE_SCRIPT_DIR/templates/README.md > README.md

printf "Creating resource directories\n"
mkdir -p src/main/resources
mkdir -p src/test/resources

if [ "$LANGUAGE_MODE" == "java" ] ; then
	echo "Nothing extra to do for java"
fi
if [ "$LANGUAGE_MODE" == "mixed" ] ; then
	printf "Creating ${BLUE}SCALA${NC} source directories\n"
	mkdir -p src/main/scala
	mkdir -p src/test/scala
fi
if [ "$LANGUAGE_MODE" == "scala" ] ; then
	printf "Creating ${BLUE}SCALA${NC} source directories\n"
	mkdir -p src/main/scala
	mkdir -p src/test/scala
	printf "Removing ${RED}JAVA${NC}  source directories\n"
	rm -rf src/main/java
	rm -rf src/test/java
fi

echo 'All set. Run this command to perform construct Eclipse artifacts and confirm build is valid:'
echo ''
echo "gradle eclipse check"
echo ''
echo "Done."

