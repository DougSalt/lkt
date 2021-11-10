#!/bin/sh
cd bin
JAR=jar
if [ $(uname) = "CYGWIN_NT-10.0" ]
then
    JAR='/cygdrive/c/Program Files/Java/jdk1.8.0_77/jar'
fi

"$JAR" cvmf ../jar-manifest.txt ../test/lt/lt.jar \
    uk/ac/hutton/lt/StructuredLookupTableExtension.class \
	uk/ac/hutton/lt/GetState.class \
	uk/ac/hutton/lt/FirstSymbol.class \
	uk/ac/hutton/lt/StructuredLookupTable.class \
	uk/ac/hutton/lt/Dimensions.class \
	uk/ac/hutton/lt/GetSymbol.class \
	uk/ac/hutton/lt/Default.class \
	uk/ac/hutton/lt/NetLogoLookupTable.class \
	uk/ac/hutton/lt/FirstState.class \
	uk/ac/hutton/lt/Group.class \
	uk/ac/hutton/lt/Symbols.class \
	uk/ac/hutton/lt/Get.class \
	uk/ac/hutton/lt/MoreSymbols.class \
	uk/ac/hutton/lt/GetDimension.class \
	uk/ac/hutton/lt/SetState.class \
	uk/ac/hutton/lt/FirstDimension.class \
	uk/ac/hutton/lt/States.class \
	uk/ac/hutton/lt/MoreDimensions.class \
	uk/ac/hutton/lt/New.class \
	uk/ac/hutton/lt/Dimension.class \
	uk/ac/hutton/lt/MoreStates.class \
	uk/ac/hutton/lt/LookupTable.class
