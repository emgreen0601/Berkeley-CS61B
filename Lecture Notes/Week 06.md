# CS 61B Week 5

## Disjoint Sets

### Dynamic Connectivity and the Disjoint Sets Problem

#### Introduction

Our data structure will implement these operations:

* connect(x, y): Connects x and y.
* isConnected(x, y): Returns true if x and y are connected. Connections can be transitive, which means that they don’t need to be direct.

For simpilicity, we will declare that all items are integers and independent from each other.

```java
ds = DisjointSets(7)
ds.connect(0, 1)
ds.connect(1, 2)
ds.connect(0, 4)
ds.connect(3, 5)
ds.isConnected(2, 4) // true
ds.isConnected(3, 0) // false

ds.connect(4, 2)
ds.connect(4, 6)
ds.connect(3, 6)
ds.isConnected(3, 0) //true
```

#### Disjoint Sets Interface

```java
public interface DisjointSets {
	/** Connects two items P and Q. */
	void connect(int p, int q);
 
	/** Checks to see if two items are connected. */
	boolean isConnected(int p, int q);
}
```

We will implement this interface to achieve these goals: 

* Number of elements N can be huge.
* Number of method calls M can be huge.
*  Calls to methods may be interspersed

Naive Approach: Record all the connections in a data structure, and do some iteration to see if one thing can be reached from each other.

Better Approaach: Ignore how things are connected, and only record sets that each belongs to.

### Quick Find

To find whether two items are connected, here are two ways:

1. List of sets of integers.

Very inituitive way, but quite slow for large N.

2. List of integers where ith entry gives set number.

connect(p, q): Change entries that equal id[p] to id[q]

```java
public class QuickFindDS implements DisjointSets {
	private int[] id;
 
	public boolean isConnected(int p, int q) {
    	    return id[p] == id[q];
	}
 
	public void connect(int p, int q) {
    	    int pid = id[p];
        	int qid = id[q];
       	for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
              	id[i] = qid;
            }
    	}
    }

    public QuickFindDS(int N) {
   	id = new int[N];
   	for (int i = 0; i < N; i++)
       	id[i] = i;
	}
} 
```

However, connecting is still slow.

### Quick Union

Instead of using random number to represent the index of sets, we could let each entry to be its parent, which results in a tree-like shape.

To connect two items, simply change the root of one item to the root of another item.

However, this method is still slow since the tree might be quite tall and the cost of the worst case is proportional to the height.

### Weighted Quick Union

We could modify Quick Union to avoid tall trees: Track tree size and link root of smaller tree to the larger one.

Thus, the `connect` and `isConnected` operation will never be slower than `logN`, which is fast enough for most programs.

Although we could track the height instead of weight, we will find out that the performance is similar.

## Asymptotics II

In this section, we will discuss more difficult examples related to run-time analysis.

### Function dup1

```java
public boolean dup1(int[] A) {
	int N = A.length;
	for (int i = 0; i < N; i += 1)
	for (int j = i + 1; j < N; j += 1)
		if (A[i] == A[j])
			return true;
	return false;
}
```

The worst case is that we have to go through every entry (the outer loop runs N times).

The number of comparisons is: `C = 1+2+3+...+(N−3)+(N−2)+(N−1) = N(N−1)/2`

Thus, since `==` is a constant time operation, the overall runtime in the worst case is Theta(N^2).

### printParty

```java
public static void printParty(int N) {
   for (int i = 1; i <= N; i = i * 2) {
      for (int j = 0; j < i; j += 1) {
         System.out.println("hello");
         int ZUG = 1 + 1;
      }
   }
}
```
Let's create a visualization to find out the runtime cost of the function above.

![printParty](https://joshhug.gitbooks.io/hug61b/content/assets/loops2_4.png "printParty")

We could conclude that `C(N) = 1 + 2 + 4 + ... + N = 2N - 1 (if N is a power of 2)`, which is in the linear family.

### Recursion (f3)

```java
public static int f3(int n) {
   if (n <= 1) 
      return 1;
   return f3(n-1) + f3(n-1);
}
```

Here's a visualization of the function above.

![recursion](https://joshhug.gitbooks.io/hug61b/content/assets/asymptotics2_tree.png "recursion")

We could conclude that the runtime cost of the function is `C(1)=1 C(2) = 1 + 2C(2)=1+2 C(3) = 1 + 2 + 4C(3)=1+2+4 C(N) = 1 + 2 + 4 + ... +C(N) = 1+2+4+...+ ??? = 2^(N−1)`, which is in the `2^N` family.

### Binary Search

Binary search is a practical way to find an item in a sorted list.
To do a binary search, we start in the middle of the list, and check if that's our desired element.

* Start in the middle of the list and check if that's our desired element. 
* If the desired element is larger, eliminate the first half of the list and return to step one.
* If the desired element is smaller, eliminate the second half of the list and return to step one.

```java
static int binarySearch(String[] sorted, String x, int lo, int hi)
    if (lo > hi) return -1;
    int m = lo + (hi - lo) / 2;
    int cmp = x.compareTo(sorted[m]);
    if (cmp < 0) return binarySearch(sorted, x, lo, m - 1);
    else if (cmp > 0) return binarySearch(sorted, x, m + 1, hi);
    else return m;
}
```
Intuitively, the runtime cost of binary search is `log_2 N`, since we can figure out that the count seems to increase by one only when `N` hits a power of 2.

We can be even more precise: `C(N) = ⌊log_2 (N)⌋+1`. Because `⌊f(N)⌋ = Θ(f(N))`, `Θ(⌊log_2 (N)⌋) = Θ(log N)`.

Log time is faster than linear time and even as better as constant time, which makes binary search an efficient algorithm.

### Merge Sort

Selection sort works off two basic steps:

* Find the smallest item among the unsorted items, move it to the front, and fix it in place.
* Sort the remaining unsorted items using selection sort.

If we analyze selection sort, we see that it's `Theta(N^2)`. To improve it, we could divide the array into two halves, sort them, and merge them, in which merging only costs `Theta(N)`.

This is the essence of merge sort:
* If the list is size 1, return. Otherwise:
* Mergesort the left half
* Mergesort the right half
* Merge the results

Mergesort has worst case runtime: `C = Theta(NlogN)`, since it has `logN` levels:
* The top level takes ~N.
* Next level takes ~N/2 + ~N/2 = ~N.
* One more level down: ~N/4 + ~N/4 + ~N/4 + ~N/4 = ~N.

`Theta(NlogN)` is better than `Theta(N^2)`, so that merge sort is better than selection sort.

## ADTs, Sets, Maps, BSTs