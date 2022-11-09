extensions [lkt table]

to example

  let data user-directory
  let tree-path (word data "/lkt.tree")
  let data-path (word data "/lkt.data")

  lkt:default "I am a default value"

  let expected-groups ["Climate" "Biophysical" "LandUse"]

  let expected-dimensions-and-symbols table:from-list [
    ["ClimateType" [ "NoClimate" "UpClimate" "DownClimate"] ]
    ["Biophys" [ "NoBiophys" "YesBiophys"] ]
    ["LandUse"   ["GL1" "GL2" "GL3" "AL1" "AL2" "AL3" "AL4" "AL5"] ]
  ]


  let expected-states table:from-list [
    [["DownClimate" "NoBiophys" "GL1"] "4.0"]
    [["NoClimate" "YesBiophys" "GL2"] "5.0"]
    [["NoClimate" "*" "GL3"] "6.0"]
    [["NoClimate" "NoBiophys" "AL1"] "4.5"]
    [["NoClimate" "NoBiophys" "AL2"] "Pickle"]
    [["NoClimate" "YesBiophys" "AL3"] "6.5"]
    [["NoClimate" "YesBiophys" "AL4"] "9.0"]
    [["NoClimate" "YesBiophys" "AL5"] "7.0"]
  ]


  let expected-dimensions table:keys expected-dimensions-and-symbols

  output-print (word "Table definition file is " tree-path)
  output-print (word "Data definition file is " data-path)
  output-print (Word "Starting testing...")

  let some-table lkt:new tree-path data-path

  ;output-print (word "Dimensions" lkt:dimensions some-table)

;  test lkt:dimensions some-table
;    expected-dimensions
;    "dimensions "
  let nof-dimensions length lkt:dimensions some-table

  let dimension 0
  lkt:first-dimension some-table
  while [ lkt:more-dimensions? some-table ]  [
    let some-dimension lkt:get-dimension some-table
    let some-group lkt:group some-table some-dimension
    ;output-print (word "     Dimension " some-dimension " in group " some-group)
    test
      item dimension expected-dimensions
      some-dimension
      "get-dimension "
    test
      item dimension expected-groups
      some-group
      "group "
    test
      lkt:symbols some-table some-dimension
      table:get expected-dimensions-and-symbols some-dimension
      "symbols"
    lkt:first-symbol some-table some-dimension
    let symbol 0
    while [ lkt:more-symbols? some-table some-dimension ] [
      let some-symbol lkt:get-symbol some-table some-dimension
      ;output-print (word "          Symbol " some-symbol )
      test
        item symbol table:get expected-dimensions-and-symbols some-dimension
        some-symbol
        "get-symbol "
      set symbol symbol + 1
    ]
    if dimension = 0 [
      foreach table:keys expected-states [ some-values ->

        test
          lkt:get some-table some-values
          table:get expected-states some-values
          "get "
        let old-value lkt:get some-table some-values
        lkt:set some-table some-values "test-value"
        test
          lkt:get some-table some-values
          "test-value"
          "get "
        lkt:set some-table some-values old-value
      ]
    ]
    set dimension dimension + 1
  ]

  test sort lkt:states some-table sort table:values expected-states "states"
  lkt:first-state some-table

  while [ lkt:more-states? some-table ] [
    test member? lkt:get-state some-table table:values expected-states true "get-state"
  ]


;  test lkt:get some-table "NoClimate" table:get expected-dimensions-and-symbols "NoClimate" "5.0" "get "
;  lkt:set some-table "NoClimate" table:get expected-dimensions-and-symbols "NoClimate"  "29.0"
;  test lkt:get some-table "NoClimate" table:get expected-dimensions-and-symbols "NoClimate" "29.0" "set "
;  test lkt:get some-table "NoClimate" ["NoClimate" "NoBiophys" "Imaginary"] "I am a default value" "default "

  output-print (Word "...ending testing.")
end

to test [actual-value expected-value message]
  if actual-value != expected-value [
    error (word "lkt:"
      message
      " expected: " expected-value
      " got: " actual-value
    )
  ]
end
@#$#@#$#@
GRAPHICS-WINDOW
7
107
54
155
-1
-1
13.0
1
10
1
1
1
0
1
1
1
-1
1
-1
1
0
0
1
ticks
30.0

BUTTON
4
10
83
43
Test
example
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

OUTPUT
135
12
574
297
12

BUTTON
5
48
75
81
Clear
clear-output
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

@#$#@#$#@
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
@#$#@#$#@
default
true
0
Polygon -7500403 true true 150 5 40 250 150 205 260 250

airplane
true
0
Polygon -7500403 true true 150 0 135 15 120 60 120 105 15 165 15 195 120 180 135 240 105 270 120 285 150 270 180 285 210 270 165 240 180 180 285 195 285 165 180 105 180 60 165 15

arrow
true
0
Polygon -7500403 true true 150 0 0 150 105 150 105 293 195 293 195 150 300 150

box
false
0
Polygon -7500403 true true 150 285 285 225 285 75 150 135
Polygon -7500403 true true 150 135 15 75 150 15 285 75
Polygon -7500403 true true 15 75 15 225 150 285 150 135
Line -16777216 false 150 285 150 135
Line -16777216 false 150 135 15 75
Line -16777216 false 150 135 285 75

bug
true
0
Circle -7500403 true true 96 182 108
Circle -7500403 true true 110 127 80
Circle -7500403 true true 110 75 80
Line -7500403 true 150 100 80 30
Line -7500403 true 150 100 220 30

butterfly
true
0
Polygon -7500403 true true 150 165 209 199 225 225 225 255 195 270 165 255 150 240
Polygon -7500403 true true 150 165 89 198 75 225 75 255 105 270 135 255 150 240
Polygon -7500403 true true 139 148 100 105 55 90 25 90 10 105 10 135 25 180 40 195 85 194 139 163
Polygon -7500403 true true 162 150 200 105 245 90 275 90 290 105 290 135 275 180 260 195 215 195 162 165
Polygon -16777216 true false 150 255 135 225 120 150 135 120 150 105 165 120 180 150 165 225
Circle -16777216 true false 135 90 30
Line -16777216 false 150 105 195 60
Line -16777216 false 150 105 105 60

car
false
0
Polygon -7500403 true true 300 180 279 164 261 144 240 135 226 132 213 106 203 84 185 63 159 50 135 50 75 60 0 150 0 165 0 225 300 225 300 180
Circle -16777216 true false 180 180 90
Circle -16777216 true false 30 180 90
Polygon -16777216 true false 162 80 132 78 134 135 209 135 194 105 189 96 180 89
Circle -7500403 true true 47 195 58
Circle -7500403 true true 195 195 58

circle
false
0
Circle -7500403 true true 0 0 300

circle 2
false
0
Circle -7500403 true true 0 0 300
Circle -16777216 true false 30 30 240

cow
false
0
Polygon -7500403 true true 200 193 197 249 179 249 177 196 166 187 140 189 93 191 78 179 72 211 49 209 48 181 37 149 25 120 25 89 45 72 103 84 179 75 198 76 252 64 272 81 293 103 285 121 255 121 242 118 224 167
Polygon -7500403 true true 73 210 86 251 62 249 48 208
Polygon -7500403 true true 25 114 16 195 9 204 23 213 25 200 39 123

cylinder
false
0
Circle -7500403 true true 0 0 300

dot
false
0
Circle -7500403 true true 90 90 120

face happy
false
0
Circle -7500403 true true 8 8 285
Circle -16777216 true false 60 75 60
Circle -16777216 true false 180 75 60
Polygon -16777216 true false 150 255 90 239 62 213 47 191 67 179 90 203 109 218 150 225 192 218 210 203 227 181 251 194 236 217 212 240

face neutral
false
0
Circle -7500403 true true 8 7 285
Circle -16777216 true false 60 75 60
Circle -16777216 true false 180 75 60
Rectangle -16777216 true false 60 195 240 225

face sad
false
0
Circle -7500403 true true 8 8 285
Circle -16777216 true false 60 75 60
Circle -16777216 true false 180 75 60
Polygon -16777216 true false 150 168 90 184 62 210 47 232 67 244 90 220 109 205 150 198 192 205 210 220 227 242 251 229 236 206 212 183

fish
false
0
Polygon -1 true false 44 131 21 87 15 86 0 120 15 150 0 180 13 214 20 212 45 166
Polygon -1 true false 135 195 119 235 95 218 76 210 46 204 60 165
Polygon -1 true false 75 45 83 77 71 103 86 114 166 78 135 60
Polygon -7500403 true true 30 136 151 77 226 81 280 119 292 146 292 160 287 170 270 195 195 210 151 212 30 166
Circle -16777216 true false 215 106 30

flag
false
0
Rectangle -7500403 true true 60 15 75 300
Polygon -7500403 true true 90 150 270 90 90 30
Line -7500403 true 75 135 90 135
Line -7500403 true 75 45 90 45

flower
false
0
Polygon -10899396 true false 135 120 165 165 180 210 180 240 150 300 165 300 195 240 195 195 165 135
Circle -7500403 true true 85 132 38
Circle -7500403 true true 130 147 38
Circle -7500403 true true 192 85 38
Circle -7500403 true true 85 40 38
Circle -7500403 true true 177 40 38
Circle -7500403 true true 177 132 38
Circle -7500403 true true 70 85 38
Circle -7500403 true true 130 25 38
Circle -7500403 true true 96 51 108
Circle -16777216 true false 113 68 74
Polygon -10899396 true false 189 233 219 188 249 173 279 188 234 218
Polygon -10899396 true false 180 255 150 210 105 210 75 240 135 240

house
false
0
Rectangle -7500403 true true 45 120 255 285
Rectangle -16777216 true false 120 210 180 285
Polygon -7500403 true true 15 120 150 15 285 120
Line -16777216 false 30 120 270 120

leaf
false
0
Polygon -7500403 true true 150 210 135 195 120 210 60 210 30 195 60 180 60 165 15 135 30 120 15 105 40 104 45 90 60 90 90 105 105 120 120 120 105 60 120 60 135 30 150 15 165 30 180 60 195 60 180 120 195 120 210 105 240 90 255 90 263 104 285 105 270 120 285 135 240 165 240 180 270 195 240 210 180 210 165 195
Polygon -7500403 true true 135 195 135 240 120 255 105 255 105 285 135 285 165 240 165 195

line
true
0
Line -7500403 true 150 0 150 300

line half
true
0
Line -7500403 true 150 0 150 150

pentagon
false
0
Polygon -7500403 true true 150 15 15 120 60 285 240 285 285 120

person
false
0
Circle -7500403 true true 110 5 80
Polygon -7500403 true true 105 90 120 195 90 285 105 300 135 300 150 225 165 300 195 300 210 285 180 195 195 90
Rectangle -7500403 true true 127 79 172 94
Polygon -7500403 true true 195 90 240 150 225 180 165 105
Polygon -7500403 true true 105 90 60 150 75 180 135 105

plant
false
0
Rectangle -7500403 true true 135 90 165 300
Polygon -7500403 true true 135 255 90 210 45 195 75 255 135 285
Polygon -7500403 true true 165 255 210 210 255 195 225 255 165 285
Polygon -7500403 true true 135 180 90 135 45 120 75 180 135 210
Polygon -7500403 true true 165 180 165 210 225 180 255 120 210 135
Polygon -7500403 true true 135 105 90 60 45 45 75 105 135 135
Polygon -7500403 true true 165 105 165 135 225 105 255 45 210 60
Polygon -7500403 true true 135 90 120 45 150 15 180 45 165 90

sheep
false
15
Circle -1 true true 203 65 88
Circle -1 true true 70 65 162
Circle -1 true true 150 105 120
Polygon -7500403 true false 218 120 240 165 255 165 278 120
Circle -7500403 true false 214 72 67
Rectangle -1 true true 164 223 179 298
Polygon -1 true true 45 285 30 285 30 240 15 195 45 210
Circle -1 true true 3 83 150
Rectangle -1 true true 65 221 80 296
Polygon -1 true true 195 285 210 285 210 240 240 210 195 210
Polygon -7500403 true false 276 85 285 105 302 99 294 83
Polygon -7500403 true false 219 85 210 105 193 99 201 83

square
false
0
Rectangle -7500403 true true 30 30 270 270

square 2
false
0
Rectangle -7500403 true true 30 30 270 270
Rectangle -16777216 true false 60 60 240 240

star
false
0
Polygon -7500403 true true 151 1 185 108 298 108 207 175 242 282 151 216 59 282 94 175 3 108 116 108

target
false
0
Circle -7500403 true true 0 0 300
Circle -16777216 true false 30 30 240
Circle -7500403 true true 60 60 180
Circle -16777216 true false 90 90 120
Circle -7500403 true true 120 120 60

tree
false
0
Circle -7500403 true true 118 3 94
Rectangle -6459832 true false 120 195 180 300
Circle -7500403 true true 65 21 108
Circle -7500403 true true 116 41 127
Circle -7500403 true true 45 90 120
Circle -7500403 true true 104 74 152

triangle
false
0
Polygon -7500403 true true 150 30 15 255 285 255

triangle 2
false
0
Polygon -7500403 true true 150 30 15 255 285 255
Polygon -16777216 true false 151 99 225 223 75 224

truck
false
0
Rectangle -7500403 true true 4 45 195 187
Polygon -7500403 true true 296 193 296 150 259 134 244 104 208 104 207 194
Rectangle -1 true false 195 60 195 105
Polygon -16777216 true false 238 112 252 141 219 141 218 112
Circle -16777216 true false 234 174 42
Rectangle -7500403 true true 181 185 214 194
Circle -16777216 true false 144 174 42
Circle -16777216 true false 24 174 42
Circle -7500403 false true 24 174 42
Circle -7500403 false true 144 174 42
Circle -7500403 false true 234 174 42

turtle
true
0
Polygon -10899396 true false 215 204 240 233 246 254 228 266 215 252 193 210
Polygon -10899396 true false 195 90 225 75 245 75 260 89 269 108 261 124 240 105 225 105 210 105
Polygon -10899396 true false 105 90 75 75 55 75 40 89 31 108 39 124 60 105 75 105 90 105
Polygon -10899396 true false 132 85 134 64 107 51 108 17 150 2 192 18 192 52 169 65 172 87
Polygon -10899396 true false 85 204 60 233 54 254 72 266 85 252 107 210
Polygon -7500403 true true 119 75 179 75 209 101 224 135 220 225 175 261 128 261 81 224 74 135 88 99

wheel
false
0
Circle -7500403 true true 3 3 294
Circle -16777216 true false 30 30 240
Line -7500403 true 150 285 150 15
Line -7500403 true 15 150 285 150
Circle -7500403 true true 120 120 60
Line -7500403 true 216 40 79 269
Line -7500403 true 40 84 269 221
Line -7500403 true 40 216 269 79
Line -7500403 true 84 40 221 269

wolf
false
0
Polygon -16777216 true false 253 133 245 131 245 133
Polygon -7500403 true true 2 194 13 197 30 191 38 193 38 205 20 226 20 257 27 265 38 266 40 260 31 253 31 230 60 206 68 198 75 209 66 228 65 243 82 261 84 268 100 267 103 261 77 239 79 231 100 207 98 196 119 201 143 202 160 195 166 210 172 213 173 238 167 251 160 248 154 265 169 264 178 247 186 240 198 260 200 271 217 271 219 262 207 258 195 230 192 198 210 184 227 164 242 144 259 145 284 151 277 141 293 140 299 134 297 127 273 119 270 105
Polygon -7500403 true true -1 195 14 180 36 166 40 153 53 140 82 131 134 133 159 126 188 115 227 108 236 102 238 98 268 86 269 92 281 87 269 103 269 113

x
false
0
Polygon -7500403 true true 270 75 225 30 30 225 75 270
Polygon -7500403 true true 30 75 75 30 270 225 225 270
@#$#@#$#@
NetLogo 6.2.2
@#$#@#$#@
@#$#@#$#@
@#$#@#$#@
@#$#@#$#@
@#$#@#$#@
default
0.0
-0.2 0 0.0 1.0
0.0 1 1.0 0.0
0.2 0 0.0 1.0
link direction
true
0
Line -7500403 true 150 150 90 180
Line -7500403 true 150 150 210 180
@#$#@#$#@
0
@#$#@#$#@
