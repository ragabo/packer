### Packing Problem
Given a set of items, each with a weight and a value, determine the number of each item to include in a collection so that the total weight is less than or equal to a given limit and the total value is as large as possible.

### Strategy Followed
- **Algorithm**: **Branch and bound** algorithm is used to solve this problem to explore branches of the tree,
which represent subsets of the solution set. Before enumerating the candidate solutions of a branch, the branch is checked against upper and lower estimated bounds on the optimal solution, and is discarded if it cannot produce a better solution than the best one found so far by the algorithm.

- **Data Structure**: A List data structure is used for representing branches of the tree
- **Regular Expression**: RegEx is used for parsing input data into correspondent meaningful object, so that OO Principle can be followed.
- **Exception Handling**: Handling exception and wrapping it into custom exception to be propagated to API caller.

### Testing
- **Unit Testing**: A test case is created with given testing data, and tested the program among these data.
