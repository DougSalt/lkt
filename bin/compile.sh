#!/bin/sh

# Doug - October 2021

# A script to compile the  plugin. I probably should use ant to do this,
# I am unaware of how the $JAVAC treats dependencies and cannot be bothered
# to find out. I have also taken this opportunity to clear out the lib
# directory, because generally people cannot be bothered working out which
# libraries they need and just bung in the last set that work - hence the
# massive list of libraries in lib/old

# Note that I am using a named removed in the clean, because there might be
# other stuff that this shouldn't clean - I am talking to you eclipse IDE.

#JAVAC=javac
JAVAC=/usr/lib/jvm/java-8-openjdk/bin/javac
CLASS_PATH=lib/netlogo-6.1.0.jar:bin:lib/scala-library.jar
if [ $(uname) = "CYGWIN_NT-10.0" ]
then
    JAVAC='/cygdrive/c/Program Files/Java/jdk1.8.0_77/bin/javac'
    CLASS_PATH="bin;lib\netlogo-6.1.0.jar;lib\scala-library.jar"
fi

if [ -n "$1" ]
then
    "$JAVAC" -d bin -cp $CLASS_PATH "$1"
    exit
fi

bin/clean.sh 2>/dev/null

"$JAVAC" -d bin -cp $CLASS_PATH \
    src/uk/ac/hutton/lt/Default.java \
	src/uk/ac/hutton/lt/Dimension.java \
	src/uk/ac/hutton/lt/Dimensions.java \
	src/uk/ac/hutton/lt/FirstDimension.java \
	src/uk/ac/hutton/lt/FirstState.java \
	src/uk/ac/hutton/lt/FirstSymbol.java \
	src/uk/ac/hutton/lt/GetDimension.java \
	src/uk/ac/hutton/lt/Get.java \
	src/uk/ac/hutton/lt/GetState.java \
	src/uk/ac/hutton/lt/GetSymbol.java \
	src/uk/ac/hutton/lt/Group.java \
	src/uk/ac/hutton/lt/LookupTable.java \
	src/uk/ac/hutton/lt/MoreDimensions.java \
	src/uk/ac/hutton/lt/MoreStates.java \
	src/uk/ac/hutton/lt/MoreSymbols.java \
	src/uk/ac/hutton/lt/NetLogoLookupTable.java \
	src/uk/ac/hutton/lt/New.java \
	src/uk/ac/hutton/lt/SetState.java \
	src/uk/ac/hutton/lt/States.java \
	src/uk/ac/hutton/lt/StructuredLookupTableExtension.java \
	src/uk/ac/hutton/lt/StructuredLookupTable.java \
	src/uk/ac/hutton/lt/Symbols.java
