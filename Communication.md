


## Class Modality
The `Communication` class is a **quasi-modal** class.

- It is not modal because it does not enforce constraints based on call sequences.

- It is not non-modal because the behavior (cost) depends on current input values.

- Fits exactly the definition of quasi-modal class from slide 5 of "Class Scope Testing"

- “Constraints on the message sequence are related to the contents of the object, but not necessarily history”


---

## Domain Constraints (from spec)

The cost of a communication depends on:

- Type: SMS or VOICE

- Duration/length:

  - SMS: number of characters in the message (measuredin hundreds, rounded up)

  - VOICE: duration in seconds

- Client’s points

- Number of friends of the sending client


The cost is calculated as it follows:
- If the size is 0, the cost is 0 cents;
- If the size is less than 10 and the client has more than 100 points, the cost is 1 cent. If the client has 100 points or fewer, the cost is 2 cents.
- If the size is 10 or more and less than 120, the cost depends on the client’s points and the communication type. If the client has fewer than 75 points, the cost is 6 cents for a text and 12 cents for a voice call. If the client has at least 75 points, the cost is 4 cents for a text. For a voice call, if the client has fewer than 4 friends, the cost is 8 cents. Otherwise, it is 5 cents.
- If the size is 120 or more and the client has fewer than 150 points, the cost is 15 cents. Otherwise, it is 12 cents.

**Additional rules:**

  - duration() applies to voice communications

  - Cost depends solely on domain inputs (not on previous calls)

---

## Method Testing for `computeCost()`

**Decision Tree:** 


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

V0: length = 0 -> cost = 0
V1: length < 10 & points > 100 -> cost = 1
V2: length < 10 & points <= 100 -> cost = 2
V3: 10 ≤ length/duration < 120 & points < 75 & SMS -> cost = 6
V4: 10 ≤ length/duration < 120 & points < 75 & VOICE -> cost = 12
V5: 10 ≤ length/duration < 120 & points >= 75 & SMS -> cost = 4 
V6: 10 ≤ length/duration < 120 & points >= 75 & VOICE & #friends < 4 -> cost = 8 
V7: 10 ≤ length/duration < 120 & points >= 75 & VOICE & #friends >= 4 -> cost = 5
V8: length/duration >= 120 & points < 150 -> cost = 15
V9: length/duration >= 120 & points >= 150 -> cost = 12

&nbsp;


|  |  |  | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 | 17 | 18 |
| ---- | -------- | --------- | ------ | ------- | ----------- | --------- | ------ | ------- | ----------- | --------------------- | ------ |----------- | --------------------- | ------ | ----------- | ---- | -------- | --------- | --------- | --------- |
| length/duration   | = 0     | ON    | 40      |       |          |            |      |
|  |        | OFF         |     | 41       |         |               |     |
|   |IN       |         |    |       |    1      |  2                | 3    |4 |5 |6 |7 |8 |9 |10 |11 |12 |13 |14|15 |16 |
| points  | >= 1      | ON    |     |       |    1      |            |      |
|  |       | OFF         |     |        |         |     0          |     |
|   | <= 9      | ON    |     |       |          |            |    9  |
|  |       | OFF         |     |        |         |       |          | 10            | 
|   |IN       |         | 4   |    5   |          |          |         |       |      1   |     2      |  3   |          4         |   5   |  6   |  7   |  8   |  9   | 1   | 2   | 3   | 4   |
| comm type   | >=  0     |   ON |       |          |          |         |       |         |          0 |     |                   |      |
|  |        | OFF         |       |          |          |         |       |         |           | -1    |        
|   | <= 200       | ON    |     |       |          |    |     | |     |         |    200  |
|  |       |    OFF         |     |       |          |    |     | |     |         |      | 201|       |  
|   |IN       |         | 90   |    100   | 110         | 120         |   130      |   140    |          |           |     |                  |    10  |20  |30  |40  |50  |60  |70  |80  |
| #friends   | >=  0     |   ON |       |          |          |         |       |         |           |     |                   |      | 0
|  |        | OFF         |       |          |          |         |       |         |           |     |  |     |   |    -1 |         
|   | <= max_friends       | ON    |     |       |          |    |     | |      |          |    |     |  |         | max_friends     |
|  |       |    OFF         |     |       |          |    |     | |     |         |      | |       |  |       | max_friends + 1 |       | 
|   |IN       |         |   max_friends | max_friends      | max_friends         |  max_friends        |   max_friends      |    max_friends   |     max_friends     |       max_friends    |  max_friends   |       max_friends            |   |       |    | |    max_friends  | 2  | 7  | 12 |
| Expected Result |  |  |  &check;|  x |  &check;|  x|  &check;|  x|  &check;|  x|  &check;|  x|  &check;| x|  &check;|  x| &check;| x|  &check;|  x;





---

