1. Copy `test/lkt/lkt.jar` to a directory `lkt` in the same directory as your
   model. This will install it locally specific to that model.  

2. If you want to install the plugin globally for all NetLoog models running on
   the computer, then create a directory `lkt`,  in the extensions folder
   within your installation of NetLogo, and copy `test/lkt/lkt.jar` to this
   directory. For typical NetLogo installations:

	+ On Mac OS X: `/Applications/NetLogo N.n.n/extensions`
	+ On 64-bit Windows with 64-bit NetLogo or 32-bit Windows with 32-bit
      NetLogo: `C:\Program Files\NetLogo N.n.n\app\extensions`
	+ On 64-bit Windows with 32-bit NetLogo: `C:\Program Files (x86)\NetLogo
      N.n.n\app\extensions`
	+ On Linux: the `app/extensions` subdirectory of the NetLogo directory
      extracted from the installation `.tgz`

      where N.n.n is the version of Netlogo

3. This subfolder (relative to your home directory):

    + On Mac OS X:` Library/Application Support/NetLogo`
    + On Windows: `AppData\NetLogo`
    + On Linux: `.netlogo`

4. The `.bundled` subfolder of the extensions folder mentioned in Item 2 (for
example, `/Applications/NetLogo N.n.n/extensions/.bundled` on Mac OS X).

To use this include the NetLogo plugin lkt in the extensions clause at the
top of the NetLogo code:

```
extensions [lkt]
```
