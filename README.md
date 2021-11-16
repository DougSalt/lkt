Lookup table for NetLogo

# INSTALLATION

The file you are looking for is `lt.jar` and maybe be found in `test/lt`.

To use it, stick the file in a sub-directory in the same directory as your
NetLogo called `lt`.

To use this, include the NetLogo plugin `lt` in the extensions clause at the
top of the NetLogo code:

```
extensions [lt]
```

# BUILD

Assumptions: you are sane and running this on one of the following:

1. Linux
2. Cygwin on Windows
3. Linux subsystem on Windows.

In theory I could do all this with `.bat` files, but will only do so, if people
really nag me about it.

Have I mentioned anywhere else how much I absolutely detest and loathe Java. I
have managed to compile this, but I have sorted out the dependencies are for
creating the NetLogo plugin, and they are few. In Scala you can build a big jar
- apparently not easily in Java. However because the dependencies in this are
trivial, i.e. only the NetLogo library, then the lt.jar is all that is needed.


## Typical development workflow.

1. Do you stuff in `src/main`, with testing in `src/test`
2. `bin/compile.sh`
3. `bin/build.sh` (does the release of the jar to the correct directory for
   testing).
4. `bin/run.sh`

Rinse and repeat

All the above can be done with: `bin/all.sh`

To clean, then `bin/clean.sh`

I have abandoned the usual conventions of of Java packaging, as NetLogo does
not tend to work that way, being Scala based.

## Contents of repository

+ bin - directory where all the scripts and compiled classes are stored. Be
  careful some IDEs automatically clear this directory on a clean - there are
  useful scripts that are not automatically generated present in this
  directory.
+ doc - documentation directory. Contains documentation. Be
  careful some IDEs automatically clear this directory on a clean - there are
  useful files that are not automatically generated present in this directory.
+ INSTALL.md - instructions on how to install the plug-in.
+ jar-manifest.txt - manifest used to create the lt.jar - the plugin.
+ lib - required libraries - these are already present in NetLogo.
+ LICENSE.txt - How you may use this plugin.
+ old-stuff - previous attempts at the plugin and redundant classes and scripts from this development iteratioin.
+ paper - A paper describing the plugin.
+ README.md - this file.
+ src - The source code for the plugin. Normally this would be something like
  `src\uk\ac\hutton\lt` or whatever. The problem with this is that the plugin
  extension for NetLogo makes it difficult to adopt this structure, therefore
  is just bunged in `src` where all classes share the default package space.
+ test - A test NetLogo model. Normally we would used automated testing. The
  problem with this is setting up things like the Context is rather involved in
  NetLogo, so we use a NetLogo model to test the plug-in. There is a README.md
  in this directory with instructions on how to do this. Also the actual plugin
  jar `lt.jar` is in a subdirectory `lt`, which is the jar you need to
  install the plugin.
+ TODO - list of outstanding tasks to complete on the plugin.

# CONTACTS

doug[dot]salt[at]hutton[dot]ac[dot]uk

