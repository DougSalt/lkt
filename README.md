# PURPOSE

Lookup table extension (lkt) for NetLogo.

An example of the a look up tree might be the following: 

```
Player_1 | Player_2 | Outcome
--------- ---------- --------
Rock     | Rock     | 0
Rock     | Paper    | 2
Paper    | Rock     | 1
Rock     | Scissors | 1
Scissors | Rock     | 2
Scissors | Scissors | 0
Paper    | Scissors | 2
Scissors | Paper    | 1
Paper    | Paper    | 0
```
This is two players, `Player_1` and `Player_2` playing the game rock, paper, scissors.

So the dimensions are `Player_1` and `Player_2`. These dimensions may only
contain the values `Rock`, `Paper` and `Scissors` and the outcomes are

+ 0 - nobody wins
+ 1 - Player\_1 wins
+ 2 - Player\_2 wins.

Thus to implement a look up table we must have two files. First a file is
required that defines the dimensions and the allowable values for those
dimensions. We denote this the tree file. The second files, denoted the data
file, is  a tab delimited file using these dimension with the last column of
such a file predicating the outcome from the previous dimension values.

The dimensions are defined by in the tree file like the following.

```
group_name_1 (group)
	dimension_1 (dimension)
		dimension_1_value_1 (value)
		dimension_1_value_2
		...
	dimension_2
	   	dimension_2_value_1
		dimension_2_value_2
		...
	...
group_name_2
	dimension_3_value_1
	...
...

```

And the data file would look similar to the following:

```
| Dimension_1         | Dimension_2         | Dimension_3         | Outcome   |
 --------------------- --------------------- --------------------- -----------
| dimension_1_value_1 | dimension_2_value_1 | dimension_3_value_1 | outcome_1 |
| dimension_1_value_2 | dimension_2_value_1 | dimension_3_value_1 | outcome_2 |
| dimension_1_value_1 | dimension_2_value_2 | dimension_3_value_1 | outcome_3 |
          .                     .                   .                  .
          .                     .                   .                  .
          .                     .                   .                  .
```

## INSTALLATION

See the [installation instructions](INSTALL.md)

# BUILD

See the [building instructions](BUILD.md)

# MANIFEST

+ bin - directory where all the scripts and compiled classes are stored. Be
  careful some IDEs automatically clear this directory on a clean - there are
  useful scripts that are not automatically generated present in this
  directory.
+ BUILD.md - instructions on how to build build the plug-in.
+ doc - documentation directory. Contains documentation. Be
  careful some IDEs automatically clear this directory on a clean - there are
  useful files that are not automatically generated present in this directory.
+ INSTALL.md - instructions on how to install the plug-in.
+ UPGRADING.md - instructions on how to install the plug-in.
+ jar-manifest.txt - manifest used to create the lkt.jar - the plugin.
+ lib - required libraries - these are already present in NetLogo.
+ README.md - this file.
+ src - The source code for the plugin. Normally this would be something like
  `src\uk\ac\hutton\lkt` or whatever. The problem with this is that the plugin
  extension for NetLogo makes it difficult to adopt this structure, therefore
  is just bunged in `src` where all classes share the default package space.
+ test - A test NetLogo model. Normally we would used automated testing. The
  problem with this is setting up things like the Context is rather involved in
  NetLogo, so we use a NetLogo model to test the plug-in. There is a README.md
  in this directory with instructions on how to do this. Also the actual plugin
  jar `lkt.jar` is in a subdirectory `lkt`, which is the jar you need to
  install the plugin.

# INSTRUCTIONS

<!-- Cut below this line and add into the Notes section of the demo Netlogo model, cbr.nlogo -->

## WHAT IS IT?

This is a small program to show how the look up table `lkt` extension works.

An example of the a look up tree might be the following: 

```
Player_1 | Player_2 | Outcome
--------- ---------- --------
Rock     | Rock     | 0
Rock     | Paper    | 2
Paper    | Rock     | 1
Rock     | Scissors | 1
Scissors | Rock     | 2
Scissors | Scissors | 0
Paper    | Scissors | 2
Scissors | Paper    | 1
Paper    | Paper    | 0
```
This is two players, `Player_1` and `Player_2` playing the game rock, paper, scissors.

So the dimensions are `Player_1` and `Player_2`. These dimensions may only
contain the values `Rock`, `Paper` and `Scissors` and the outcomes are

+ 0 - nobody wins
+ 1 - Player_1 wins
+ 2 - Player_2 wins.

Thus to implement a look up table we must have two files. First a file is
required that defines the dimensions and the allowable values for those
dimensions. We denote this the tree file. The second files, denoted the data
file, is  a tab delimited file using these dimension with the last column of
such a file predicating the outcome from the previous dimension values.

The dimensions are defined by in the tree file like the following.

```
group_name_1 (group)
	dimension_1 (dimension)
		dimension_1_value_1 (value)
		dimension_1_value_2
		...
	dimension_2
	   	dimension_2_value_1
		dimension_2_value_2
		...
	...
group_name_2
	dimension_3_value_1
	...
...

```

This is a tree with the topmost node at the top of the file and the dimensions
represent additional layers to the tree, with the symbols that make up those
layers forming the nodes or branches of the tree.


And the data file would look similar to the following:

```
| Dimension_1         | Dimension_2         | Dimension_3         | Outcome   |
 --------------------- --------------------- --------------------- -----------
| dimension_1_value_1 | dimension_2_value_1 | dimension_3_value_1 | outcome_1 |
| dimension_1_value_2 | dimension_2_value_1 | dimension_3_value_1 | outcome_2 |
| dimension_1_value_1 | dimension_2_value_2 | dimension_3_value_1 | outcome_3 |
          .                     .                   .                  .
          .                     .                   .                  .
          .                     .                   .                  .
```

There is one reserved character which is the "*" character allowing the mapping
of defaults. This must appear in both the input and the data file of the tree.

## HOW IT WORKS

This is a reference program to show a simple implementation of the `lkt` extension

If the "Test" button is selected and no error messages are displayed then the extension is working as expected.

## HOW TO USE IT

The extensions has the following primitives. 

1. new
2. dimensions
3. first-dimension
4. get-dimension
5. more-dimensions?
6. symbols
7. first-symbol
8. get-symbol
9. more-symbols?
10. get 
11. states
12. first-state
13. get-state
14. more-states?
15. group
16. set 
17. default

Each of these primitives is now defined in terms of its parameters, what it returns and what its purpose is

### new

Creates a lookup table.

A lookup table.

This look up table definition file looks is made of the following atoms defined in the tree file. 


```
group_name_1 (group)
	dimension_1 (dimension)
		dimension_1_value_1 (value)
		dimension_1_value_2
		...
	dimension_2
	   	dimension_2_value_1
		dimension_2_value_2
		...
	...
group_name_2
	dimension_3_value_1
	...
...

```

where the spaces _must_ be tabs (so check your editor does not replace tabs
with spaces). Atoms may only contain numeric and alphabetic upper case or lower
case letters. There are case sensitive.

That is there are *values* which are the nominals for a *dimension* a dimension
belongs to a *group*. *group* are purely documentary and allow the author to
logical group the dimensions into what makes sense. dimensions have to be
unique with dimensions, values do not.

The data file is of the following format.

```
Dimension_1           Dimension_2           Dimension_3           Outcome
dimension_1_value_1   dimension_2_value_1   dimension_3_value_1   outcome_1
dimension_1_value_2   dimension_2_value_1   dimension_3_value_1   outcome_2
dimension_1_value_1   dimension_2_value_2   dimension_3_value_1   outcome_3
*                     *                     dimension_3_value_1   outcome_4
```

Again this is tab delimited,  the first row represents the names of the
dimensions and subsequent rows are values that make up the values of that
dimension. "*" means any valid dimension found not defined in another
combination of values.

#### Parameters

+ *Table definition file* This contains a space delimited definition file for the look up table.

+ *Data file* This is space delimited file contain values in the form of a standard CSV where commas have been replaced with spaces. This of course means that dimensions may 

#### Returns

A lookup table (lookup table object).

### dimensions

Returns a list of possible dimensions from the lookup table definition file.

#### Parameters

+ A look up table (lookup table object)

#### Returns

A list of dimension names. (list)

### first-dimension

Reset a dimension iterator over a lookup table definition file to the first
dimension.

#### Parameters

+ Table definition file (lookup table object)

#### Returns

Nothing.

### get-dimension

Gets the next dimension in the lookup table definition file.

#### Parameters

+ Table definition file (lookup table object)

#### Returns

The dimension name (string).

### more-dimensions?

Returns true if the are more dimensions in lookup table definition file to
inspect, false otherwise.

#### Parameters

+ Table definition file (lookup table object)

#### Returns

True or False.

### symbols

Returns a list of symbols from a lookup table definition file.

#### Parameters

+ Table definition file (lookup table object)

+ Dimension (string)

#### Returns

A lookup table.

### first-symbol

Reset a symobol iterator over a lookup table definition file to the first
symbol of the named dimension.

#### Parameters

+ A look up table (lookup table object)

+ Particular dimension (string)

#### Returns

Nothing

### get-symbol

#### Parameters

+ A look up table (lookup table object)

+ Particular dimension (string)

#### Returns

The next symbol for that dimension.

### more-symbols?

Returns true if the are more symbols in lookup table definition file to
inspect for that named dimension, false otherwise.

#### Parameters

+ A look up table (lookup table object)

+ Particular dimension (string)

#### Returns

True or false.

### states

Iterates through all the states in no particular order.

#### Parameters

+ A look up table (lookup table object)

#### Returns

+ An outcome (string)

### first-state

#### Parameters

+ A look up table (lookup table object)

#### Returns

A lookup table.

### get-state

Iterates over the outcomes or states of a particular look-up table. This is
effectively the predicated results from specific sets of states.

#### Parameters

+ A look up table (lookup table object)

#### Returns

An outcome or a state (string).

### more-states?

Iterating through the outcomes for a particular look up table, then are there
any more outcomes available. True if there are, false otherwise.

#### Parameters

+ A look up table (lookup table object)

#### Returns

True or false.

### group

This determines the name of a the group to which the dimension belongs.

#### Parameters

+ A look up table (lookup table object)

+ A named dimenison (string)

#### Returns

+ A group name (string).

### get 

Gets a particular outcome or state based on the specified value for a group of
dimensions.

#### Parameters

+ A look up table (lookup table object)

+ A set of values (list of Strings)

#### Returns

A state or outcome (string)

### set 

Overwrites a particular entry in the look-up table.

#### Parameters

+ A look up table (lookup table object)

+ A set of values (list of Strings)

+ A value (string).

#### Returns

Nothing.

### default

Set the default value returned, if a pattern is not found in a look up table.

#### Parameters

+ A look up table (lookup table object)

#### Returns

Nothing

## THINGS TO NOTICE

Each of the primitives is made of use in the code. Notice nothing will happen if the code is working as expected

## THINGS TO TRY

Extend the dimensions in the `test/lk.tree` file and add more rows to the
`files/lk.data` file with more states.

## EXTENDING THE MODEL

The code can be used as the basis for any NetLogo model using a look-up table, with dimension of any size and consituted by any set of symbols.

## NETLOGO FEATURES

This uses a tree structures, which allows a differing number of symbols per dimension, or even state. This means you can extend NetLogo with user-defined ordinals, if the coder so desires.

## RELATED MODELS

This will and can be used in conjunction with the case-based reasoning extension to implement many land-use models.

## CREDITS AND REFERENCES

### Authors

+ doug.salt@hutton.ac.uk
+ gary.polhill@hutton.ac.uk

### Repository

https://gitlab.com/doug.salt/lkt.git

