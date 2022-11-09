#!/bin/sh
if [ $(uname) = "CYGWIN_NT-10.0" ]
then
    cd /cygdrive/c/Program\ Files/NetLogo\ 6.2.2/
    ~/.netlogo/NetLogo\ 6.2.2/NetLogo "\\users\\ds42723\\git\\lkt\\test\\lkt.nlogo"
elif [ $(uname) = "Darwin" ]
then
    ~/netlogo/NetLogo\ 6.2.2/netlogo-gui.sh $(pwd)/test/lkt.nlogo
else
    ~/.netlogo/NetLogo\ 6.2.2/NetLogo $(pwd)/test/lkt.nlogo
fi

