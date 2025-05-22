
# Method Scope Analysis for `computeCost()` – Class `Communication`

---

## 1. **Test Design Strategy Used**

**Strategy:** Combinational Function Test

This strategy is chosen because the method `computeCost()`:

* Has **complex conditional logic**
* Depends on **domain constraints**, not on sequence or internal state
* Class classifies as **quasi-modal**

The method is pure (returns a value) and behaves differently based on combinations of:

* `length` or `duration`
* `commtype` (SMS or VOICE)
* `points` of the client
* `#friends` of the client (in specific VOICE conditions)

---

## Step by step analysis


### Step 1. Identify Input Parameters 

* `length/duration` (integer)
* `type` (SMS or VOICE)
* `points` (integer)
* `#friends` (integer)

&nbsp;

### Step 2: Build the Decision Tree

* Top level: `length` or `duration`
* Nested conditions: `points` and `#friends`
* Final leaves: cost (in cents)

  &nbsp;

  computeCost()
  ├── length/duration = 0
  ││   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── V0: cost = 0
  ││
  ├── length/duration < 10
  ││      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── points > 100
  ││         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── V1: cost = 1
  ││   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── else:
  ││       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── V2: cost = 2
  ││
  ├── 10 ≤ length/duration < 120
  ││      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── points < 75
  ││      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── Comm Type: SMS 
  ││   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; └── V3: cost = 6
  ││      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── Comm Type: VOICE
  ││   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; └── V4: cost = 12
  ││ &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  └── points ≥ 75
  ││      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── Comm Type: SMS 
  ││   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; └── V5: cost = 4  
  ││      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── Comm Type: VOICE
  ││   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── #friends < 4
  ││   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; └── V6: cost = 8
  ││ &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── else
  ││  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── V7: cost = 5
  ├── length/duration >= 120
  ││ &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  ├── points < 150 
  ││   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── V8: cost = 15
  ││       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; └── else
  ││  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── V9: cost = 12

&nbsp;


### Step 3: Extract Test Vectors (Leaf Nodes)

V0: length = 0 --> cost = 0
V1: length < 10 & points > 100 --> cost = 1
V2: length < 10 & points <= 100 --> cost = 2
V3: 10 ≤ length/duration < 120 & points < 75 & SMS --> cost = 6
V4: 10 ≤ length/duration < 120 & points < 75 & VOICE --> cost = 12
V5: 10 ≤ length/duration < 120 & points >= 75 & SMS --> cost = 4 
V6: 10 ≤ length/duration < 120 & points >= 75 & VOICE & #friends < 4 --> cost = 8 
V7: 10 ≤ length/duration < 120 & points >= 75 & VOICE & #friends >= 4 --> cost = 5
V8: length/duration >= 120 & points < 150 --> cost = 15
V9: length/duration >= 120 & points >= 150 --> cost = 12


&nbsp;
&nbsp;

0 <= #points <= 200
CommType = VOICE | SMS
0 <= #friends <= max_friends  ---> max_friends = 5*terminals - 3

&nbsp;
V0: length = 0 --> cost = 0

| V0 |  |  | 1 | - | - | ... | ... | ... | ... | ... | ... |  ... | ... | ... | ... |... |.... |
| ---- | -------- | --------- | ------ | ------- | ------- | ------- |------- | ------- |------- | -| ------- |------- |------ |------- | ------- |------- |------- |
| length/duration   | = 0            | ON | 0 |    |    |   |   |   |   |  |    |   |   |   |   |      
|                   |                | OFF|   |  1 |    |   |   |   |   |  |    |   |   |   |   |      
|                   |                | OFF|   |    | -1 |   |   |   |   |  |    |   |   |   |   |      
|                   |  IN            |    |   |    |    | 0 | 0 | 0  | 0  | 0 | 0 | 0 | 0 | 0 | 0 |   0 | 0 | 0 | 0 |    
|  points           |  >= 0          | ON |   |    |    | 0 |   |   |   |  |    |   |   |   |   |      
|                   |                | OFF|   |    |    |   | -1|   |   |  |    |   |   |   |   |      
|                   |  <= 200        | ON |   |    |    |   |   |200|   |  |    |   |   |   |   |      
|                   |                | OFF|   |    |    |   |   |   |201|  |    |   |   |   |   |
|                   |  IN            |    | 11  | 10 | 9 | 8 | 7 |   |   | 1 | 2 | 3  | 4 | 5 | 6  |
|  CommType         |  SMS           | ON |     |    |    |   |   |   |   | SMS |    |   |   |   |   |
|                   |                | OFF|     |    |    |   |   |   |   |  |  VOICE  |   |   |   |   |
|                   |  VOICE         | ON |     |    |    |   |   |   |   |  |    | VOICE  |   |   |   |
|                   |                | OFF|     |    |    |   |   |   |   |  |    |   | SMS  |   |   |
|                   |  IN            |    | SMS | SMS| SMS| SMS| SMS| SMS| SMS| |    |   |   | SMS  | SMS  | SMS  | SMS  | SMS  | SMS  |
|  #friends         |  >= 0          | ON |     |    |    |   |   |   |   |  |    |   |   | 0  |   |
|                   |                | OFF|     |    |    |   |   |   |   |  |    |   |   |   | -1  |
|                   |  <= max_friends| ON |     |    |    |   |   |   |   |  |    |   |   |   |   | max_friends 
|                   |                | OFF|     |    |    |   |   |   |   |  |    |   |   |   |   ||  max_friends + 1 |   |
|                   |  IN            |    | max_friends | max_friends | max_friends | max_friends|max_friends |  max_friends | max_friends  | max_friends |  max_friends  | max_friends  | max_friends  |   |   |
| Expected Result   |                |    | V0|  V2 | x |  V0|  x | V0 |  x|  V0 | x |  V0|  x | V0 |  x|  V0 | x | 


Only one test case from this Boundary Table is relevant since if length/duration = 0 then any of the other variables can have any other values and the result will be the same.

&nbsp;&nbsp;


&nbsp;
&nbsp;
&nbsp;



V1: length < 10 & points > 100 --> cost = 1


| V1                  |                 |     | 2            | -            | 3            | -          | ...         | ...          | ...          | ...          | ...          | ...          | ...          | ... | 
| ------------------- | --------------- | --- | ------------ | ------------ | ------------ | ------------ | ------------ | ------------ | ------------ | ------------ | ------------ | ------------ | ------------ |------------ | 
| length/duration | < 10          | ON  | 10            |              |              |              |              |              |              |              |              |              |              |     |     |              |                  |     |     |
|                     |                 | OFF |              | 9           |              |              |              |              |              |              |              |              |              |     |     |              |                  |     |     |
|                     | IN              |     |             |             | 1            | 2            | 3            | 4            | 5            | 6            | 7            | 8            |     9         |  1   |  2   |     3         |       4           |     |     |
| points          | > 100         | ON  |              |              |            100  |              |              |              |              |              |              |              |              |     |     |              |                  |     |     |
|                     |                 | OFF |              |              |              |        101      |              |              |              |              |              |              |              |     |     |              |                  |     |     |
|                     | IN              |     | 101          | 150          |           |           | 101          | 150          | 102          | 103         | 104         |   104           |     105         |  105   |  106   |      107        |        108          |   109  |  110   |
| CommType        | SMS             | ON  |              |              |              |              |        SMS      |              |              |           |              |              |              |     |     |              |                  |     |     |
|                     |                 | OFF |              |              |              |              |              |         VOICE     |              |              |         |              |              |     |     |              |                  |     |     |
|                     | VOICE           | ON  |              |              |              |              |              |              |      VOICE        |              |              |         |              |     |     |              |                  |     |     |
|                     |                 | OFF |              |              |              |              |              |              |              |     SMS         |              |              |           |     |     |              |                  |     |     |
|                     | IN              |     | SMS          | SMS          | SMS          | SMS          |           |           |           |              |  SMS            |     SMS         |   SMS           | SMS | SMS | SMS          | SMS              | SMS | SMS |
| #friends        | >= 0            | ON  |              |              |              |              |              |              |              |              |  0            |              |              |    |     |              |                  |     |     |
|                     |                 | OFF |              |              |              |              |              |              |              |              |              |      -1        |              |     |   |              |                  |     |     |
|                     | <= max_friends | ON  |              |              |              |              |              |              |              |              |              |              |    max_friends          |     |     |  |                  |     |     |
|                     |                 | OFF |              |              |              |              |              |              |              |              |              |              |              |  max_friends + 1   |     |              |  |     |     |
|                     | IN              |     | max_friends | max_friends | max_friends | max_friends | max_friends | max_friends | max_friends | max_friends |  |  |  |     |   max_friends   |      max_friends         |        max_friends           |     |     |
| **Expected Result** |                 |     | V5           | V1            | V2            | V1           | V1            | V1           | V1            |  V1          | V1            | x           | V1           | V1  | V1   | V1           | V1                |     |     |

&nbsp;
&nbsp;
&nbsp;

V2: length < 10 & points <= 100 --> cost = 2


| V2 |  |  | 4 | - | 5 | - | ... | ... | ... | ... | ... |  ... | ... | ... |
| ---- | -------- | --------- | ------ | ------- | ------- | ------- |------- | ------- |------- | -| ------- |------- |------ |------- |
| length/duration   | < 10           | ON | 10|    |    |   |   |   |   |  |    |   |   |   |   |      
|                   |                | OFF|   | 9  |    |   |   |   |   |  |    |   |   |   |   |       
|                   |  IN            |    |   |    | 1  | 2 | 3 | 4 | 5 | 6| 7 | 8 | 9 | 1 | 2 | 3 | 4 | 5 | 6 |    
|  points           |  <= 100        | ON |   |    | 100|   |   |   |   |  |    |   |   |   |   |      
|                   |                | OFF|   |    |    |101|   |   |   |  |    |   |   |   |   |      
|                   |  IN            |    |90|80 |    |   |99|98|97|96|95|94|93|92|91|92|91|
|  CommType         |  SMS           | ON |   |    |    |   |SMS|   |   ||    |   |   |   |   |
|                   |                | OFF|   |    |    |   |   | VOICE  |   |  |    |   |   |   |   |
|                   |  VOICE         | ON |   |    |    |   |   |   | VOICE  |  |    |   |   |   |   |
|                   |                | OFF|   |    |    |   |   |   |   | SMS  |    |   |   |   |   |
|                   |  IN            |    |SMS| SMS| SMS| SMS| | | | |  SMS  | SMS  |  SMS | SMS  | SMS  | SMS  | SMS  | SMS  | SMS  |
|  #friends         |  >= 0          | ON |     |    |    |   |   |   |   |  | 0   |   |   |   |   |
|                   |                | OFF|     |    |    |   |   |   |   |  |    | -1  |   |   |   |
|                   |  <= max_friends| ON |     |    |    |   |   |   |   |  |    |   | max_friends  |   |   |  
|                   |                | OFF|     |    |    |   |   |   |   |  |    |   |   | max_friends+1  |   ||  |   |
|                   |  IN            |    | max_friends | max_friends | max_friends | max_friends|max_friends |  max_friends | max_friends  | max_friends |    |   |   |   |   |
| Expected Result   |                |    | V5|  V2 | V2 |  V1|  V2 | V2 |  V2|  V2 | V2 |  x|  V2 | V2|  V2|  V2 | V2 | 


&nbsp;
&nbsp;
&nbsp;

V3: 10 ≤ length/duration < 120 & points < 75 & SMS --> cost = 6

| V3 |  |  | 6 | - | 7 | - | 8 | - | 9 | - | ... |  ... | ... | ... | 
| ---- | -------- | --------- | ------ | ------- | ------- | ------- |------- | ------- |------- | -| ------- |------- |------ |------- |
| length/duration   | >= 10          | ON | 10|    |    |   |   |   |   |  |    |   |   |   |   |      
|                   |                | OFF|   | 9  |    |   |   |   |   |  |    |   |   |   |   |    
|                   | < 120          | ON |   |    |120 |   |   |   |   |  |    |   |   |   |   |      
|                   |                | OFF|   |    |    |119|   |   |   |  |    |   |   |   |   |    
|                   |  IN            |    |   |    |    |   | 10| 11|12 |13|14  |15 |16 |17 |18 |19 | 20 |21 |22 |    
|  points           |  < 75          | ON |   |    |    |   |75 |   |   |  |   |   |   |   |   |   |      
|                   |                | OFF|   |    |    |   |   |74 |   |  |    |   |   |   |   |      
|                   |  IN            |    |10 |20  |30  |40 |   |   | 50|60|70|71|72|73|74|69|68|
|  CommType         |  SMS           | ON |   |    |    |   |   |   |  SMS |   |    |   |   |   |   |
|                   |                | OFF|   |    |    |   |   |   |   | VOICE |    |   |   |   |   |
|                   |  IN            |    |SMS| SMS| SMS| SMS| SMS | SMS| | |  SMS  | SMS  |  SMS | SMS  | SMS  | SMS  | SMS  | SMS  | SMS  |
|  #friends         |  >= 0          | ON |     |    |    |   |   |   |   |  |  0  |   |   |   |   |
|                   |                | OFF|     |    |    |   |   |   |   |  |    | -1  |   |   |   |
|                   |  <= max_friends| ON |     |    |    |   |   |   |   |  |    |   | max_friends  |   |   |  
|                   |                | OFF|     |    |    |   |   |   |   |  |    |   |   | max_friends + 1  |   |   |   |
|                   |  IN            |    | max_friends | max_friends | max_friends | max_friends|max_friends |  max_friends | max_friends  | max_friends |    |   |   |   |   |
| Expected Result   |                |    | V3|  V2 | V8 |  V3|  V5 | V3 |  V3|  V4 | V3 |  x|  V3 | V3 |  V3|  V3 | V2 | 

&nbsp;
&nbsp;
&nbsp;

V4: 10 ≤ length/duration < 120 & points < 75 & VOICE --> cost = 12

| V4 |  |  | 10 | - | 11 | - | 12 | - | 13 | - | ... |  ... | ... | ... | 
| ---- | -------- | --------- | ------ | ------- | ------- | ------- |------- | ------- |------- | -| ------- |------- |------ |------- |
| length/duration   | >= 10          | ON | 10|    |    |   |   |   |   |  |    |   |   |   |   |      
|                   |                | OFF|   | 9  |    |   |   |   |   |  |    |   |   |   |   |    
|                   | < 120          | ON |   |    |120 |   |   |   |   |  |    |   |   |   |   |      
|                   |                | OFF|   |    |    |119|   |   |   |  |    |   |   |   |   |    
|                   |  IN            |    |   |    |    |   | 10| 11|12 |13|14  |15 |16 |17 |18 |19 | 20 |21 |22 |    
|  points           |  < 75          | ON |   |    |    |   |75 |   |   |  |   |   |   |   |   |   |      
|                   |                | OFF|   |    |    |   |   |74 |   |  |    |   |   |   |   |      
|                   |  IN            |    |10 |20  |30  |40 |   |   | 50|60|70|71|72|73|74|69|68|
|  CommType         |  VOICE         | ON |   |    |    |   |   |   |  VOICE |   |    |   |   |   |   |
|                   |                | OFF|   |    |    |   |   |   |   | SMS |    |   |   |   |   |
|                   |  IN            |    |VOICE| VOICE| VOICE| VOICE| VOICE | VOICE| | |  VOICE  | VOICE  |  VOICE | VOICE  | VOICE  | VOICE  | VOICE  | VOICE  | VOICE  |
|  #friends         |  >= 0          | ON |     |    |    |   |   |   |   |  |  0  |   |   |   |   |
|                   |                | OFF|     |    |    |   |   |   |   |  |    | -1  |   |   |   |
|                   |  <= max_friends| ON |     |    |    |   |   |   |   |  |    |   | max_friends  |   |   |  
|                   |                | OFF|     |    |    |   |   |   |   |  |    |   |   | max_friends + 1  |   |   |   |
|                   |  IN            |    | max_friends | max_friends | max_friends | max_friends|max_friends |  max_friends | max_friends  | max_friends |    |   |   |   |   |
| Expected Result   |                |    | V4|  V2 | V8 |  V4|  V6 or V7 (depending on the number of terminals) | V4 |  V4|  V3 | V4 |  x|  V4 | V4 |  V4|  V4 

&nbsp;
&nbsp;
&nbsp;

V5: 10 ≤ length/duration < 120 & points >= 75 & SMS --> cost = 4

| V5 |  |  | 14 | - | 15 | - | 16 | - | 17 | - | ... |  ... | ... | ... | 
| ---- | -------- | --------- | ------ | ------- | ------- | ------- |------- | ------- |------- | -| ------- |------- |------ |------- |
| length/duration   | >= 10          | ON | 10|    |    |   |   |   |   |  |    |   |   |   |   |      
|                   |                | OFF|   | 9  |    |   |   |   |   |  |    |   |   |   |   |    
|                   | < 120          | ON |   |    |120 |   |   |   |   |  |    |   |   |   |   |      
|                   |                | OFF|   |    |    |119|   |   |   |  |    |   |   |   |   |    
|                   |  IN            |    |   |    |    |   | 10| 11|12 |13|14  |15 |16 |17 |18 |19 | 20 |21 |22 |    
|  points           |  >= 75         | ON |   |    |    |   |75 |   |   |  |   |   |   |   |   |   |      
|                   |                | OFF|   |    |    |   |   |74 |   |  |    |   |   |   |   |      
|                   |  IN            |    |75 |76/101  |77/150  |78 |   |   | 79|80|81|82|83|84|85|86|87|
|  CommType         |  SMS           | ON |   |    |    |   |   |   |  SMS |   |    |   |   |   |   |
|                   |                | OFF|   |    |    |   |   |   |   | VOICE |    |   |   |   |   |
|                   |  IN            |    |SMS| SMS| SMS| SMS| SMS | SMS| | |  SMS  | SMS  |  SMS | SMS  | SMS  | SMS  | SMS  | SMS  | SMS  |
|  #friends         |  >= 0          | ON |     |    |    |   |   |   |   |  |  0  |   |   |   |   |
|                   |                | OFF|     |    |    |   |   |   |   |  |    | -1  |   |   |   |
|                   |  <= max_friends| ON |     |    |    |   |   |   |   |  |    |   | max_friends  |   |   |  
|                   |                | OFF|     |    |    |   |   |   |   |  |    |   |   | max_friends + 1  |   |   |   |
|                   |  IN            |    | max_friends | max_friends | max_friends | max_friends|max_friends |  max_friends | max_friends  | max_friends |    |   |   |   |   |
| Expected Result   |                |    | V5|  V2 or V1 (depending on the input of number of points >= 75 & > 100) | V8 or V9 (depending on the input number of points >= 75 & >= 150) |  V5|  V5  | V3 |  V5|   V6 or V7 (depending on the number of terminals) | V5 |  x|  V5 | V5 |  V5|  V5 

&nbsp;
&nbsp;
&nbsp;

V6: 10 ≤ length/duration < 120 & points >= 75 & VOICE & #friends < 4 --> cost = 8 

| V6 |  |  | 18 | - | 19 | - | 20| - | 21 | - | 22 |  - | 
| ---- | -------- | --------- | ------ | ------- | ------- | ------- |------- | ------- |------- | -| ------- |------- |
| length/duration   | >= 10          | ON | 10|    |    |   |   |   |   |  |    |   |   |   |   |      
|                   |                | OFF|   | 9  |    |   |   |   |   |  |    |   |   |   |   |    
|                   | < 120          | ON |   |    |120 |   |   |   |   |  |    |   |   |   |   |      
|                   |                | OFF|   |    |    |119|   |   |   |  |    |   |   |   |   |    
|                   |  IN            |    |   |    |    |   | 10| 11|12 |13|14  |15 |16 |17 |18 |19 | 20 |21 |22 |    
|  points           |  >= 75         | ON |   |    |    |   |75 |   |   |  |   |   |   |   |   |   |      
|                   |                | OFF|   |    |    |   |   |74 |   |  |    |   |   |   |   |      
|                   |  IN            |    |75 |76 /101 |77/150  |78 |   |   | 79|80|81|82|83|84|85|86|87|
|  CommType         |  VOICE         | ON |   |    |    |   |   |   |  VOICE |   |    |   |   |   |   |
|                   |                | OFF|   |    |    |   |   |   |   | SMS |    |   |   |   |   |
|                   |  IN            |    |VOICE| VOICE| VOICE| VOICE| VOICE | VOICE| | |  VOICE  | VOICE  |  VOICE | VOICE  | VOICE  | VOICE  | VOICE  | VOICE  | VOICE  |
|  #friends         | < 4            | ON |     |    |    |   |   |   |   |  |  4  |   |   |   |   |
|                   |                | OFF|     |    |    |   |   |   |   |  |    | 3  |   |   |   |
|                   |  IN            |    | 1 | 2 | 3 | 1|2 |  3 | 1  | 2 |    |   | 3  |  1 |   |
| Expected Result   |                |    | V6|  V2 or V1 (depending on the number of points >= 75 & > 100) | V8 or V9 (depending on the number of points >= 75 && >= 150) |  V6|  V6  | V4 |  V6|  V5 | V7 |  V6|  V6 | V6 |

&nbsp;
&nbsp;
&nbsp;

V7: 10 ≤ length/duration < 120 & points >= 75 & VOICE & #friends >= 4 --> cost = 5


| V7 |  |  | 23 | - | 24 | - | 25| - | 26 | - | 27 |  - | 
| ---- | -------- | --------- | ------ | ------- | ------- | ------- |------- | ------- |------- | -| ------- |------- |
| length/duration   | >= 10          | ON | 10|    |    |   |   |   |   |  |    |   |   |   |   |      
|                   |                | OFF|   | 9  |    |   |   |   |   |  |    |   |   |   |   |    
|                   | < 120          | ON |   |    |120 |   |   |   |   |  |    |   |   |   |   |      
|                   |                | OFF|   |    |    |119|   |   |   |  |    |   |   |   |   |    
|                   |  IN            |    |   |    |    |   | 10| 11|12 |13|14  |15 |16 |17 |18 |19 | 20 |21 |22 |    
|  points           |  >= 75         | ON |   |    |    |   |75 |   |   |  |   |   |   |   |   |   |      
|                   |                | OFF|   |    |    |   |   |74 |   |  |    |   |   |   |   |      
|                   |  IN            |    |75 |76/101  |77/150  |78 |   |   | 79|80|81|82|83|84|85|86|87|
|  CommType         |  VOICE         | ON |   |    |    |   |   |   |  VOICE |   |    |   |   |   |   |
|                   |                | OFF|   |    |    |   |   |   |   | SMS |    |   |   |   |   |
|                   |  IN            |    |VOICE| VOICE| VOICE| VOICE| VOICE | VOICE| | |  VOICE  | VOICE  |  VOICE | VOICE  | VOICE  | VOICE  | VOICE  | VOICE  | VOICE  |
|  #friends         | >= 4            | ON |     |    |    |   |   |   |   |  |  4  |   |   |   |   |
|                   |                | OFF|     |    |    |   |   |   |   |  |    | 3  |   |   |   |
|                   |  IN            |    | 4 | 5 | 6 | 7|8 |  9 | 4  | 5 |    |   | 6  |  7 |   |
| Expected Result   |                |    | V7|  V2 or V1 (depending on the input of number of points >= 75 & > 100) | V8 or V9 (depending on the input of number of points >= 75 && >= 150) |  V7|  V7  | V4 |  V7|  V5 | V7 |  V6|  V7 | V7 |

&nbsp;
&nbsp;
&nbsp;

V8: length/duration >= 120 & points < 150 --> cost = 15


| V8 |  |  | 28 | - | 29 | - | ...| ... |... |... |... |  ... | ... | ... | 
| ---- | -------- | --------- | ------ | ------- | ------- | ------- |------- | ------- |------- | -| ------- |------- |------ |------- |
| length/duration   | >= 120         | ON |120|    |    |   |   |   |   |  |    |   |   |   |   |      
|                   |                | OFF|   | 119|    |   |   |   |   |  |    |   |   |   |   |     
|                   |  IN            |    |   |    |120 |121|122|123|124|125|126  |127 |128 |129 |130 |131 |132 |133 |134 |    
|  points           |  < 150         | ON |   |    |150 |   |   |   |   |  |   |   |   |   |   |   |      
|                   |                | OFF|   |    |    |149|   |   |   |  |    |   |   |   |   |      
|                   |  IN            |    |149|148 |132  |147 | 146  | 145  |144|143|142|141|140|139|138|137|136|
|  CommType         |  SMS           | ON |   |    |    |   |SMS|   |   ||    |   |   |   |   |
|                   |                | OFF|   |    |    |   |   | VOICE  |   |  |    |   |   |   |   |
|                   |  VOICE         | ON |   |    |    |   |   |   | VOICE  |  |    |   |   |   |   |
|                   |                | OFF|   |    |    |   |   |   |   | SMS  |    |   |   |   |   |
|                   |  IN            |    |SMS| SMS| SMS| SMS| | | | |  SMS  | SMS  |  SMS | SMS  | SMS  | SMS  | SMS  | SMS  | SMS  |
|  #friends         |  >= 0          | ON |     |    |    |   |   |   |   |  | 0   |   |   |   |   |
|                   |                | OFF|     |    |    |   |   |   |   |  |    | -1  |   |   |   |
|                   |  <= max_friends| ON |     |    |    |   |   |   |   |  |    |   | max_friends  |   |   |  
|                   |                | OFF|     |    |    |   |   |   |   |  |    |   |   | max_friends+1  |   ||  |   |
|                   |  IN            |    | max_friends | max_friends | max_friends | max_friends|max_friends |  max_friends | max_friends  | max_friends |    |   |   |   |   |                  |  IN            |    | 4 | 5 | 6 | 7|8 |  9 | 4  | 5 |    |   | 6  |  7 |   |
| Expected Result   |                |    | V8|  V3 or V4 or V5 or V6 or V7 (depends on the input of number of points: points < 100 & points < 75 & points >= 75; on the input of type of communication SMS & VOICE; and on the input of the number of friends) | V9 |  V8|  V8  | V8 |  V8|  V8 | V8 |  x|  V8 | V8 |

&nbsp;
&nbsp;
&nbsp;

V9: length/duration >= 120 & points >= 150 --> cost = 12


| V9 |  |  | 30 | - | 31 | - | ...| ... |... |... |... |  ... | ... | ... | 
| ---- | -------- | --------- | ------ | ------- | ------- | ------- |------- | ------- |------- | -| ------- |------- |------ |------- |
| length/duration   | >= 120         | ON |120|    |    |   |   |   |   |  |    |   |   |   |   |      
|                   |                | OFF|   | 119|    |   |   |   |   |  |    |   |   |   |   |     
|                   |  IN            |    |   |    |120 |121|122|123|124|125|126  |127 |128 |129 |130 |131 |132 |133 |134 |    
|  points           |  >= 150        | ON |   |    |150 |   |   |   |   |  |   |   |   |   |   |   |      
|                   |                | OFF|   |    |    |149|   |   |   |  |    |   |   |   |   |      
|                   |  IN            |    |151|152 |153  |154 | 155  | 156  |157|158|159|160|160|160|160|160|160|
|  CommType         |  SMS           | ON |   |    |    |   |SMS|   |   ||    |   |   |   |   |
|                   |                | OFF|   |    |    |   |   | VOICE  |   |  |    |   |   |   |   |
|                   |  VOICE         | ON |   |    |    |   |   |   | VOICE  |  |    |   |   |   |   |
|                   |                | OFF|   |    |    |   |   |   |   | SMS  |    |   |   |   |   |
|                   |  IN            |    |SMS| SMS| SMS| SMS| | | | |  SMS  | SMS  |  SMS | SMS  | SMS  | SMS  | SMS  | SMS  | SMS  |
|  #friends         |  >= 0          | ON |     |    |    |   |   |   |   |  | 0   |   |   |   |   |
|                   |                | OFF|     |    |    |   |   |   |   |  |    | -1  |   |   |   |
|                   |  <= max_friends| ON |     |    |    |   |   |   |   |  |    |   | max_friends  |   |   |  
|                   |                | OFF|     |    |    |   |   |   |   |  |    |   |   | max_friends+1  |   ||  |   |
|                   |  IN            |    | max_friends | max_friends | max_friends | max_friends|max_friends |  max_friends | max_friends  | max_friends |    |   |   |   |   |                  |  IN            |    | 4 | 5 | 6 | 7|8 |  9 | 4  | 5 |    |   | 6  |  7 |   |
| Expected Result   |                |    | V9|  V5 or V6 or V7 (depends on the input of type of communication SMS & VOICE and on the input of number of friends) | V9 |  V8|  V9  | V9 |  V9|  V9 | V9 |  x|  V9 | V9 |

---

