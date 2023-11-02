#!/bin/sh
cd bin
JAR=jar
if [ $(uname) = "CYGWIN_NT-10.0" ]
then
    JAR='/cygdrive/c/Program Files/Java/jdk1.8.0_77/jar'
elif [ $(uname) = "Darwin" ]
then
    JAR="/usr/bin/jar"
fi

"$JAR" cvmf ../jar-manifest.txt ../test/lkt/lkt.jar \
    uk/ac/hutton/lkt/StructuredLookupTableExtension.class \
	uk/ac/hutton/lkt/GetState.class \
	uk/ac/hutton/lkt/FirstSymbol.class \
	uk/ac/hutton/lkt/StructuredLookupTable.class \
	uk/ac/hutton/lkt/Dimensions.class \
	uk/ac/hutton/lkt/GetSymbol.class \
	uk/ac/hutton/lkt/Default.class \
	uk/ac/hutton/lkt/NetLogoLookupTable.class \
	uk/ac/hutton/lkt/FirstState.class \
	uk/ac/hutton/lkt/Group.class \
	uk/ac/hutton/lkt/Symbols.class \
	uk/ac/hutton/lkt/Get.class \
	uk/ac/hutton/lkt/MoreSymbols.class \
	uk/ac/hutton/lkt/GetDimension.class \
	uk/ac/hutton/lkt/SetState.class \
	uk/ac/hutton/lkt/FirstDimension.class \
	uk/ac/hutton/lkt/States.class \
	uk/ac/hutton/lkt/MoreDimensions.class \
	uk/ac/hutton/lkt/New.class \
	uk/ac/hutton/lkt/Dimension.class \
	uk/ac/hutton/lkt/MoreStates.class \
	uk/ac/hutton/lkt/LookupTable.class
