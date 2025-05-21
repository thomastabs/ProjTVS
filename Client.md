


## Class Modality
The `Client` class is a **non-modal** class.

---

## Class Invariant

- `name`: Client's name (string)
- `terminals`: Set of terminals owned by the client
- `points`: Integer representing the client's points
- `friends`: Set of other Client objects

The invariant is defined as follows:


INV(Client):
1. name.length ≤ 40
2. 1 ≤ |terminals| ≤ 9
3. 0 ≤ points ≤ 200
4. 0 ≤ |friends| ≤ 5 * |terminals| − 3
5. ∀ c ∈ clients: c ∉ c.friends / client ∉ client.friends (no self-friendship) -> cond 2
6. ∀ t₁, t₂ ∈ client.terminals: if t₁.id == t₂.id then t₁ = t₂ (unique terminals) -> cond 1


---

## Boundary Matrix

|  |  |  | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 | 17 | 18 |
| ---- | -------- | --------- | ------ | ------- | ----------- | --------- | ------ | ------- | ----------- | --------------------- | ------ |----------- | --------------------- | ------ | ----------- | ---- | -------- | --------- | --------- | --------- |
| c.name   | <= 40      | ON    | 40      |       |          |            |      |
|  |        | OFF         |     | 41       |         |               |     |
|   |IN       |         |    |       |    1      |  2                | 3    |4 |5 |6 |7 |8 |9 |10 |11 |12 |13 |14|15 |16 |
| #terminals  | >= 1      | ON    |     |       |    1      |            |      |
|  |       | OFF         |     |        |         |     0          |     |
|   | <= 9      | ON    |     |       |          |            |    9  |
|  |       | OFF         |     |        |         |       |          | 10            | 
|   |IN       |         | 4   |    5   |          |          |         |       |      1   |     2      |  3   |          4         |   5   |  6   |  7   |  8   |  9   | 1   | 2   | 3   | 4   |
| #points   | >=  0     |   ON |       |          |          |         |       |         |          0 |     |                   |      |
|  |        | OFF         |       |          |          |         |       |         |           | -1    |        
|   | <= 200       | ON    |     |       |          |    |     | |     |         |    200  |
|  |       |    OFF         |     |       |          |    |     | |     |         |      | 201|       |  
|   |IN       |         | 90   |    100   | 110         | 120         |   130      |   140    |          |           |     |                  |    10  |20  |30  |40  |50  |60  |70  |80  |
| #friends   | >=  0     |   ON |       |          |          |         |       |         |           |     |                   |      | 0
|  |        | OFF         |       |          |          |         |       |         |           |     |  |     |   |    -1 |         
|   | <= max_friends       | ON    |     |       |          |    |     | |      |          |    |     |  |         | max_friends     |
|  |       |    OFF         |     |       |          |    |     | |     |         |      | |       |  |       | max_friends + 1 |       | 
|   |IN       |         |   max_friends | max_friends      | max_friends         |  max_friends        |   max_friends      |    max_friends   |     max_friends     |       max_friends    |  max_friends   |       max_friends            |   |       |    | |    max_friends  | 2  | 7  | 12 |
| c.ti   | cond 1 | ON    |   |         |    |       |          |          |         |       |          |           |     |                   |   |       |    T| |      |     |       |          |            |      |
|  |        | OFF         |   |         |    |       |          |          |         |       |          |           |    |                   |   |       |    |F |     |     |       |          |            |      |   |     |
|   |IN       |         |  T  |  T   |   T      |  T  |    T   | T |    T     |  T  |   T    | T |  T |    T     |  T  |   T    |      |                  | T  | T  |
| client !in friends  | cond 2 | ON    |   |         |    |       |          |          |         |       |          |           |     |                   |   |       |    | |    T  |     |       |t          |            |      |
|  |        | OFF         |   |         |    |       |          |          |         |       |          |           |    |                   |   |       |    | |     | F    |       |          |            |      |   |     |
|   |IN       |         |  T  |    T   |     T    |       T           |  T   |   T   |   T   |   T   |   T   |   T   |   T   |   T   |   T   |   T   |   T   |   T   | 
| Expected Result |  |  |  &check;|  x |  &check;|  x|  &check;|  x|  &check;|  x|  &check;|  x|  &check;| x|  &check;|  x| &check;| x|  &check;|  x;

* `max_friends = 5 * terminals − 3`
* ON and OFF points for each condition are chosen near the boundaries of the domain.

---

