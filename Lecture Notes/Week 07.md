# CS 61B Week 07

## B-Trees (2-3, 2-3-4 Trees)

Height of BST
* Worst case: `Theta(N)`
* Best case: `Theta(log N)`

![Height of BST ](https://joshhug.gitbooks.io/hug61b/content/assets/Screen%20Shot%202019-03-05%20at%2012.56.54%20PM.png "Height of BST ")

O is just an upper bound, rather than the worst case. However, many people use O as a shorthand for worst case.

### BST Performance

* depth: the number of links between a node and the root.
* height: the lowest depth of a tree, which determines the worst case of runtime.
* average depth: average of the total depths in the tree, which determines the average-case runtime.

### BST insertion order

If we insert nodes in random order, it will actually end up being relatively bushy, in which the average depth and height are expected to be `Theta(log N)`.

However, in the real world situations, data are unlikely inserted randomly.

### B-Trees

![2-3-4 Tree](https://joshhug.gitbooks.io/hug61b/content/assets/Screen%20Shot%202019-03-05%20at%204.12.17%20PM.png "2-3-4 Tree")


The tree showed above is called **B-Tree** or **2-3-4 Tree**. 2-3-4 and 2-3 refer to the number of children each node can have. 

The process of adding a node to a 2-3-4 tree is:

* Similar to BST, we compare new item with each node in the tree and insert it into the leaf node.
* If the node has 4 items, pop up the middle left item and re-arrange the children accordingly.
* If the parent node having 4 nodes, pop up the middle left node again, rearranging the children accordingly.
* Repeat this process until the parent node can accommodate or you get to the root.

### B-tree Runtime Analysis

The worst case runtime of searching a B-tree:
* Each node had the maximum number of elements
* Traverse all the way to bottom

The amount of operations will be `L log N`. Since `L` is constant, the runtime is `O(log N)`. B-tree is complex, but it could handle insertion operations in any order.

## Red Black Trees

We will create a new tree structure similar to B-trees, since B-trees are really difficult to implement.

### Rotating Trees

rotateLeft(G): Let x be the right child of G. Make G the new left child of x.

rotateRight(G): Let x be the left child of G. Make G the new right child of x.

Below is a graphical description of what happens in a left rotation on the node G.

![Rotating Left](https://joshhug.gitbooks.io/hug61b/content/assets/Screen%20Shot%202019-03-06%20at%2010.25.18%20PM.png)

We could also rotate a non-root node.

![None Root Node](https://joshhug.gitbooks.io/hug61b/content/assets/Screen%20Shot%202019-03-06%20at%2010.37.17%20PM.png)

### Red Black Trees

We could use a red link to convert a 3-node to BST tree. We choose arbitrarily to make the left element a child of the right one. The structure is called left-leaning red-black trees (LLRB).

![Red Link](https://joshhug.gitbooks.io/hug61b/content/assets/Screen%20Shot%202019-03-06%20at%2010.56.51%20PM.png)

#### Properties

* Left-Leaning Red-Black trees have a 1-1 correspondence with 2-3 trees.
* No node has 2 red links.
* There are no red right-links.
* Every path from root to leaf has same number of black links.
* Height is no more than 2x height of corresponding 2-3 tree.

#### Inserting into LLRB

* While inserting, use a red link.
* If there is a right leaning "3-node", rotate left the appropriate node to fix.
* If there are two consecutive left links, rotate right the appropriate node to fix.
* If there are any nodes with two red children, color flip the node to emulate the split operation.

#### Runtime

Because a LLRB tree has a 1-1 correspondence with a 2-3 tree and will always remain within 2x the height of its 2-3 tree, the runtimes of the operations will take `log N` time.

## Hashing