See [here](https://github.com/NetLogo/NetLogo-Libraries#netlogo-libraries) for iinstructions.

Fork the repository ihttps://github.com/NetLogo/NetLogo-Libraries in github.com. This is done by clicking the fork button in the right hand corner of the screen. Then clone this repository

```
git clone git@github.com:/DougSalt/NetLogo-Libraries
```

or if you have used this before then on the main page of github for that
project, switch to the 6.1 branch and pull to the state of the master.
You need to update your local version of NetLogo-libraries, locally:

```
git pull upstream 6.1
```

Make a new branch

```
cd NetLogo-Libraries
git checkout -b lkt
# I don't think you need to do this line, but useful for pulling upstream.
git remote add upstream git@github.com:NetLogo/NetLogo-Libraries.git
```

copy the latest version of test/lkt/lkt.jar into the correct zip format
by using the command

```
cd ~/git/lkt/test/lkt
zip lkt-1.0.0.zip lkt.jar
cp lkt-1.0.0.zip ~/git/NetLogo-Libraries/extensions
```

where `1.0.0` is the version number

Add this file to `NetLogo-Libraries/extensions` directory.

Add the following to the `NetLogo-Libraries/libraries.conf`


{
    name: "Look up table"
    codeName: "lkt"
    shortDescription: "Look up table implementation."
    longDescription: """Implements a NetLogo extension that uses tree definition files for defining dimensions (which, in a look up table and like R is usually  a "factor" or categorical variable), and then a data file mapping the values of these dimensions to outcomes.
"""
    version: "1.0.0"
    homepage: "https://github.com/DougSalt/lkt.git"
    downloadURL: "https://raw.githubusercontent.com/NetLogo/NetLogo-Libraries/6.1/extensions/lkt-1.0.0.zip"
}

then

```
git add extensions/lkt.jar
git commit -a -m "Adding the lookup table extension."
git push
```

The go up to https://github.com/DougSalt/NetLogo and generate a pull request back to the original repository.

