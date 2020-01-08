# CS 61B Week 08

## Heaps and PQs

### Priority Queue Interface

We could use the abstract data type "priority queue" to find the smallest or largest element quickly instead of searching. There can be memory benefits to using this data structure.

```java
/** (Min) Priority Queue: Allowing tracking and removal of 
  * the smallest item in a priority queue. */
public interface MinPQ<Item> {
    /** Adds the item to the priority queue. */
    public void add(Item x);
    /** Returns the smallest item in the priority queue. */
    public Item getSmallest();
    /** Removes the smallest item from the priority queue. */
    public Item removeSmallest();
    /** Returns the size of the priority queue. */
    public int size();
}
```

### Tree Representation

There are many approaches we can take to representing trees.

#### Approach 1a, 1b, and 1c

We will create mappings between nodes and their children.

```java
public class Tree1A<Key> {
  Key k;
  Tree1A left;
  Tree1A middle;
  Tree1A right;
  ...
}
```

![Tree1A](https://joshhug.gitbooks.io/hug61b/content/assets/Screen%20Shot%202019-03-09%20at%209.54.04%20PM.png "Tree1A")

```java
public class Tree1B<Key> {
  Key k;
  Tree1B[] children;
  ...
}
```

![Tree1B](https://joshhug.gitbooks.io/hug61b/content/assets/Screen%20Shot%202019-03-09%20at%2010.03.15%20PM.png "Tree1B")

```java
public class Tree1C<Key> {
  Key k;
  Tree1C favoredChild;
  Tree1C sibling;
  ...
}
```

![Tree1C](https://joshhug.gitbooks.io/hug61b/content/assets/Screen%20Shot%202019-03-09%20at%2010.08.44%20PM.png "Tree1C")

#### Approach 2

We will store the keys array as well as a parents array.

```java
public class Tree2<Key> {
  Key[] keys;
  int[] parents;
  ...
}
```

![Tree2](https://joshhug.gitbooks.io/hug61b/content/assets/Screen%20Shot%202019-03-09%20at%2010.15.11%20PM.png "Tree2")

#### Approach 3

We assume that our tree is complete.

```java
public class Tree3<Key> {
  Key[] keys;
  ...
}
```

![Tree3](https://joshhug.gitbooks.io/hug61b/content/assets/Screen%20Shot%202019-03-09%20at%2010.26.05%20PM.png "Tree3")

### Implementation

Let's consider the data structures we already know:

Ordered Array

* `add`: `Theta(N)`
* `getSmallest`: `Theta(1)`
* `removeSmallest`: `Theta(N)`

Bushy BST

* `add`: `Theta(log N)`
* `getSmallest`: `Theta(log N)`
* `removeSmallest`: `Theta(log N)`

HashTable

* `add`: `Theta(1)`
* `getSmallest`: `Theta(N)`
* `removeSmallest`: `Theta(N)`

Currently, BST is the most efficient data structure, but it can't deal with collisions.

### Heap Structure

Binary min-heap as being **complete** and obeying **min-heap** property:

* Min-heap: Every node is less than or equal to both of its children
* Complete: Missing items only at the bottom level (if any), all nodes are as far left as possible.

![Heap](https://joshhug.gitbooks.io/hug61b/content/assets/heap-13.2.1.png "Heap")

We will use `Tree3` to implement the heap structure. We may leave one empty spot at the beginning to simplify computation. 

* `leftChild(k)` = k * 2
* `rightChild(k)` = k * 2 + 1
* `parent(k)` = k / 2

### Heap Operations

* `add`: Add to the end of heap temporarily. Swim up the hierarchy to the proper place. (Swimming involves swapping nodes if child < parent)
* `getSmallest`: Return the root of the heap.
* `removeSmallest`: Swap the last item in the heap into the root. Sink down the hierarchy to the proper place.

Here's the code of swim operation.

```java
public void swim(int k) {
    if (keys[parent(k)] ≻ keys[k]) {
       swap(k, parent(k));
       swim(parent(k));              
    }
}
```

Runtime for each operation:

* `add`: `Theta(log N)`
* `getSmallest`: `Theta(1)`
* `removeSmallest`: `Theta(log N)`