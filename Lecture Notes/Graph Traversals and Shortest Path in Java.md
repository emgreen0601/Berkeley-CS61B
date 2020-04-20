# Graph Traversals and Shortest Path in Java

In this article, you'll learn:

 - A quick review of the tree structure and how to traverse a tree.
 - What is a graph structure and how to traverse it.
 - How to find the shortest path between each vertex in a graph.

If you are preparing for job interviews or programming competitions, you've come to the right place because questions related to Graph Traversals and Shortest Path are popular in both scenarios.

## Tree (Review)

Trees are well-known as a non-linear data structure. Different from Linked Lists or Arrays, they don’t store data in a linear way. Instead, they organize data hierarchically.

### Techinical Definition

A tree is a set of **nodes** or **vertices**. **Nodes** are connected by a set of **edges**, which manage the relationship between nodes. Each node contains a **value**, and there is **exactly one path** between any two nodes.

A rooted tree is a tree with a designated **root**. Every node except the root has exactly one **parent**. A node can have 0 or more **children**. The example below shows a valid tree with three levels of nodes, in which node D is the root, node B and node F are the children of the root, and the root is the parent of node B and node F.

![Example Tree](https://joshhug.gitbooks.io/hug61b/content/assets/Screen%20Shot%202019-03-17%20at%203.53.23%20PM.png)

### Trees Traversals

We could iterate through a linear data structure (such as a list) simply from its first item to the last, or maybe we could do it through the reverse of the list. Tree traversal works like iterating through a list, visiting each node exactly once. However, it's complicated to find the correct order. Currently, there are a few natural ways to traverse through a tree:

 - Level Order Traversal
 - Depth-First Traversal 
 - - Pre-order 
 - - In-order 
 - - Post-order
 
### Level Order Traversal

Iterate the tree by levels, from left to right.
* First level: D
* Second level: B F
* Third level: A C E G

Output:
```
D B F A C E G
```

### Depth-First Traversal

#### Pre-order Traversal

* Visit the root node.
* Visit all the nodes in the left subtree.
* Visit all the nodes in the right subtree.

```java
preOrder(BSTNode x) {
    if (x == null) return;
    print(x.key)
    preOrder(x.left)
    preOrder(x.right)
}
```
Output:
```
D B A C F E G
```

#### In-order Traversal

* Visit all the nodes in the left subtree.
* Visit the root node.
* Visit all the nodes in the right subtree.

```java
inOrder(BSTNode x) {
    if (x == null) return;    
    inOrder(x.left)
    print(x.key)
    inOrder(x.right)
}
```
Output:
```
A B C D E F G
```

#### Post-order Traversal

* Visit all the nodes in the left subtree.
* Visit all the nodes in the right subtree.
* Visit the root node.

```java
postOrder(BSTNode x) {
    if (x == null) return;    
    postOrder(x.left)
    postOrder(x.right)
    print(x.key)   
}
```
Output:
```
A C B E G F D
```

## Graphs Definition

### Graph

Graphs can be used to show relationships between objects or to represent different types of networks. A graph consists of a set of nodes (or vertices), which are connected with a set of zero or more edges. All trees are also graphs, but not all graphs are trees. Here are some types of graphs:

#### Simple Graphs and Multi-Graphs
Graphs can be divided into two categories: simple graphs and multi-graphs. In a simple graph, there's only one edge between each of two nodes, and there's no edge from a node to itself, which will create a loop. 

Moreover, there are other ways to categorize graphs:
- Undirected graphs contain bidirectional edges and directed graphs contain directed edges. Edges in directed graphs have arrows.
- Cyclic graphs contain at least one graph cycle, and Acyclic graphs do not have graph cycles.

#### More Definitions

* Vertices with an edge between are **adjacent**.
* Vertices or edges may have labels or weights.
* A sequence of vertices connected by edges is a **path**.
* A path of which first and last vertices are the same is a cycle.
* Two vertices are **connected** when there is a path between them.
* If all vertices are connected, the graph is connected.

## Graph API and Traversals

### Graph API 

Here is a Graph API we will use to implement the traversal algorithms. However, we won't introduce the details of how to implement a graph in this article.

```java
public class Paths {
    public Paths(Graph G, int s)    // Find all paths from G
    boolean hasPathTo(int v)        // Whether there is a path from s to v
    Iterable<Integer> pathTo(int v) // path from s to v
}
```

### Graph Traversals

Similar to tree traversals, graph traversal refers to the process of visiting each vertex in a graph. There are two popular algorithms to traverse a graph:

* Depth First Search: Visit the child vertices before visiting the neighbors. (Similar to Depth First Traversal of trees)
* Breadth First Search: Visit the neighbours before visiting the child vertices. (Similar to Level Order Traversal of trees)

### DFS (Depth First Search)

This algorithm of exploring a neighbor’s entire subgraph before moving on to the next neighbor is known as Depth First Search. This kind of traversal could find a path from vertex `s` to every other reachable vertices, visiting each vertex at most once.

First, we will create the class `DepthFirstPaths` and define other variables:

- `marked`: Record whether a vertex is visited to prevent a infinite loop between a vertex and its parent or child.
- `edgeTo`: Record the parent of each vertex. It is a map that helps us track how we get to each node.
- `s`: The vertex which we will begin the traversal.

```java
public class DepthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private int s;

    public DepthFirstPaths(Graph G, int s) {
        ...             // Data structure initialization
        dfs(G, s);      // Recursively find vertices connected to s
    }
    ...
```

Here's how to implement the recursive DFS algorithm.

**Pseudocode**
* Take vertex `v` as the input.
* Mark `v`.
* For each unmarked neighbour vertex `w`:
* * Set `edgeTo[w] = v`.
* * Run these steps for `w`.

**Java Code**
```java
...
   private void dfs(Graph G, int v) {
       marked[v] = true;
       for (int w: G.adj(v)) { 	   // for each neighbour vertex w
           if (!marked[w]) {       // check whether w is marked
               edgeTo[w] = v;
               dfs(G, w);
           }
       }
   }
...
```

After performing the depth first search,  we could use the `edgeTo` array to find the path from the first vertex `s` to any reachable vertex.

`hasPathTo` will return whether there's a path between vertex `s` and `v`.  If `v` is marked,  we could know that  `v` is reached by DFS.

`pathTo` will return a list of vertex, which is the path **from vertex `s` to `v`**. We will start from vertex `v`, find its parent vertex `x = edgeTo[v]`, and repeat the process until we reach the vertex `s`. 

```java
...
	public boolean hasPathTo(int v) {
	    return marked[v];
	}
	
	public Iterable <Integer> pathTo(int v) {
	    if (!hasPathTo(v)) return null;
	    List <Integer> path = new ArrayList <>();
	    // start from v, find its parent vertex until we reach the vertex s
	    for (int x = v; x != s; x = edgeTo[x]) {
	        path.add(x);
	    }
	    path.add(s);
	    // since we iterate from vertex v to vertex s, we should reverse the list
	    Collections.reverse(path);
	    return path;
	}
}
```

### BFS (Breadth First Search)

The algorithm of exploring a vertex's immediate children before moving on to its grandchildren is known as Breadth First Search. In other words, we visit all nodes one edge from the source, and then all nodes two edges from our source, etc.

First, we will create the class `BreadthFirstPaths` and define other variables:

- `marked`: Record whether a vertex is visited to prevent a infinite loop between a vertex and its parent or child.
- `edgeTo`: Record the parent of each vertex. It is a map that helps us track how we get to each vertex.
- `distTo`: Record the distance from each vertex to the starting vertex.

```java
public class BreadthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;
    ...
```

Here's how to implement the BFS algorithm.

**Pseudocode**
* Initialize a queue with the starting vertex and mark that vertex.
* Repeat until the queue is empty:
* * Remove vertex `v` from the front of the queue.
* * For each unmarked neighbor `n` of `v`:
* * * Mark `n`.
* * * Add `n` to the end of the queue.
* * * Set `edgeTo[n] = v`.
* * * Set `distTo[n] = distTo[v] + 1`.

**Java Code**
```java
	...
    private void bfs(Graph G, int s) {
        Queue<Integer> fringe = new Queue<Integer>();
        fringe.enqueue(s);
        marked[s] = true;
        distTo[s] = 0;
        while (!fringe.isEmpty()) {
            int v = fringe.dequeue();
            for (int w: G.adj(v)) {
                if (!marked[w]) {
                    fringe.enqueue(w);
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                }
            }
        }
    }
}
```

## Shortest Path

### Dijkstra's Algorithm

What if we want to find the shortest path from a starting vertex to each reachable vertex? Instead of using BFS, we could use Dijkstra's Algorithm which takes into account edge distances (edge weights). As long as the edges are all non-negative, Dijkstra's is guaranteed to be optimal. Here's how it works:

* Create a priority queue.
* Add `s` to the priority queue with priority 0. 
* Add all other vertices to the priority queue with priority infinity.
* While the priority queue is not empty, and **relax** all of the edges going out from the vertex.

#### Relax

* Remove vertex `v` from the priority queue, iterate through its edges.
* For the `edge (v,w)`, compare the `currentBestDistToV + weight(v,w)` and `currentBestDistToW`.
* If the former is smaller, set the `currentBestDistToW = currentBestDistToV + weight(v,w)`, and set `edgeTo[w] = v`.
* Never relax edges that point to already visited vertices.

#### Pseudocode

```
def dijkstras(source):
    PQ.add(source, 0)
    For all other vertices, v, PQ.add(v, infinity)
    while PQ is not empty:
        p = PQ.removeSmallest()
        relax(all edges from p)

def relax(edge p,q):
   if q is visited (or q is not in PQ):
       return

   if distTo[p] + weight(edge) < distTo[q]:
       distTo[q] = distTo[p] + w
       edgeTo[q] = p
       PQ.changePriority(q, distTo[q])
```

#### Proof

* At start, `distTo[source] = 0`.
* After relaxing all edges from source, assume `v1` is the closet vertex to the source.
* Suppose there's another path `(s,va,vb,...,v1)`, which is shorter than `(s,v1)`.
* Since `(s,va)` is longer than `(s,v1)`, that path doesn't exist.
* Thus, `v1` is the closet vertex to the source, and that argument is still valid for all the edges of `v1`.
