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
    src/uk/ac/hutton/lkt/Default.java \
	src/uk/ac/hutton/lkt/Dimension.java \
	src/uk/ac/hutton/lkt/Dimensions.java \
	src/uk/ac/hutton/lkt/FirstDimension.java \
	src/uk/ac/hutton/lkt/FirstState.java \
	src/uk/ac/hutton/lkt/FirstSymbol.java \
	src/uk/ac/hutton/lkt/GetDimension.java \
	src/uk/ac/hutton/lkt/Get.java \
	src/uk/ac/hutton/lkt/GetState.java \
	src/uk/ac/hutton/lkt/GetSymbol.java \
	src/uk/ac/hutton/lkt/Group.java \
	src/uk/ac/hutton/lkt/LookupTable.java \
	src/uk/ac/hutton/lkt/MoreDimensions.java \
	src/uk/ac/hutton/lkt/MoreStates.java \
	src/uk/ac/hutton/lkt/MoreSymbols.java \
	src/uk/ac/hutton/lkt/NetLogoLookupTable.java \
	src/uk/ac/hutton/lkt/New.java \
	src/uk/ac/hutton/lkt/SetState.java \
	src/uk/ac/hutton/lkt/States.java \
	src/uk/ac/hutton/lkt/StructuredLookupTableExtension.java \
	src/uk/ac/hutton/lkt/StructuredLookupTable.java \
	src/uk/ac/hutton/lkt/Symbols.java
