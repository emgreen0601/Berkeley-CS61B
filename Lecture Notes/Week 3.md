# CS 61B Week 3

## ALists, Resizing, vs. SLists

### Limitation of DLists

Suppose we added `get(int i)`, which returns the ith item of the list. While we have a quite long DList, this operation will be significantly slow.

By instead, we could use Array to build a list without links.

### Random Access in Arrays

Retrieval from any position of an array is very fast, which is independent of the size of it.

### Naive AList Code

```java
public class AList {
    private int[] items;
    private int size;

    public AList() {
        items = new int[100];
        size = 0;
    }

    public void addLast(int x) {
        items[size] = x;
        size += 1;
    }

    public int getLast() {
        return items[size - 1];
    }

    public int get(int i) {
        return items[i];
    }

    public int size() {
        return size;
    }
}
```

Here are some invariants of this piece of code:

*  The position of the next item to be inserted is always `size`.
* `size` is always the number of items in the AList.
* The last item in the list is always in position `size - 1`.

### Delete Operation

```java
public int removeLast() {
    int returnItem = items[size - 1];
    items[size - 1] = 0;
    size -= 1;
    return returnItem;
}
```

### Naive Resizing Arrays

The limitation of the above data structure is that the size of array is fixed.

To solve that problem, we could simply build a new array that is big enough to accomodate the new data. For example, we can imagine adding the new item as follows:

```java
public void addLast(int x) {
  if (size == items.length) {
    int[] a = new int[size + 1];
    System.arraycopy(items, 0, a, 0, size);
    items = a;  	
  }
  items[size] = x;
  size += 1;
}
```
The problem is that this method has terrible performance when you call `addLast` a lot of times. The time required is exponential instead of linear for SLList.

Geometric resizing is much faster: Just how much better will have to wait. (This is how the Python list is implemented.)

```java
public void addLast(int x) {
  if (size == items.length) {
	resize(size * 2);
  }
  items[size] = x;
  size += 1;
}
```

### Memory Performance

Our AList is almost done, but we have one major issue. Suppose we insert 1,000,000,000 items, then later remove 990,000,000 items. In this case, we'll be using only 10,000,000 of our memory boxes, leaving 99% completely unused.

To fix this issue, we can also downsize our array when it starts looking empty. Specifically, we define a "usage ratio" R which is equal to the size of the list divided by the length of the `items` array. For example, in the figure below, the usage ratio is 0.04.

### Generic Array

When creating an array of references to Item:

* `(Item []) new Object[cap];`
* Causes a compiler warning, which you should ignore.

The another change to our code is that we will delete an item by setting it to `null` instead of `0`, which could be collected by Java Garbage Collector.

## Testing

When you are writing programs, it's normal to have errors, and instead of using the autograder, we could write tests for ourselves.

We will write a test method `void testSort()` first to test a `sort()` method before we really implement the method.

### Ad Hoc Testing

We could easily create a test, such as the code below, which will return nothing or the first mismatch of the provided arrays.

We should not use `==` while comparing two array objects because this operation will simply compare the memory address pointed by the two variables.

```java
public class TestSort {
    /** Tests the sort method of the Sort class. */
    public static void testSort() {
        String[] input = {"i", "have", "an", "egg"};
        String[] expected = {"an", "egg", "have", "i"};
        Sort.sort(input);
        for (int i = 0; i < input.length; i += 1) {
            if (!input[i].equals(expected[i])) {
                System.out.println("Mismatch in position " + i + ", expected: " + expected + ", but got: " + input[i] + ".");
                break;
            }
        }
    }

    public static void main(String[] args) {
        testSort();
    }
}
```

### JUnit

With the JUnit library, our test method could be simplified to the following codes:

```java
public static void testSort() {
    String[] input = {"i", "have", "an", "egg"};
    String[] expected = {"an", "egg", "have", "i"};
    Sort.sort(input);
    org.junit.Assert.assertArrayEquals(expected, input);
}
```

### Selection Sort

Basic steps of selection sort:

* Find the smallest item.
* Move it to the front.
* Selection sort the remaining N-1 items (without touching the front item).

#### findSmallest()

Since `<` can't compare strings, we could use the `compareTo` method.

```java
public class Sort {
    /** Sorts strings destructively. */
    public static void sort(String[] x) { 
           // find the smallest item
           // move it to the front
           // selection sort the rest (using recursion?)
    }

    /** Returns the smallest string in x. */
    public static String findSmallest(String[] x) {
        String smallest = x[0];
        for (int i = 0; i < x.length; i += 1) {
            int cmp = x[i].compareTo(smallest);
            if (cmp < 0) {
                smallest = x[i];
            }
        }
        return smallest;
    }
}
```

Test the method above:

```java
public static void testFindSmallest() {
    String[] input = {"i", "have", "an", "egg"};
    String expected = "an";

    String actual = Sort.findSmallest(input);
    org.junit.Assert.assertEquals(expected, actual);        

    String[] input2 = {"there", "are", "many", "pigs"};
    String expected2 = "are";

    String actual2 = Sort.findSmallest(input2);
    org.junit.Assert.assertEquals(expected2, actual2);
}
```

#### Swap

```java
public static void swap(String[] x, int a, int b) {
    String temp = x[a];
    x[a] = x[b];
    x[b] = temp;
}
```

```java
public static void testSwap() {
    String[] input = {"i", "have", "an", "egg"};
    int a = 0;
    int b = 2;
    String[] expected = {"an", "have", "i", "egg"};

    Sort.swap(input, a, b);
    org.junit.Assert.assertArrayEquals(expected, input);
}
```

### Recursive Array Helper Method

Since Java doesn't support array slicing (`x[1:2]`), we should add a helper method to recursively sort the whole array.

```java
public static void sort(String[] x) {
    sort(x, 0);
}

public static void sort(String[] x, int start) {
    smallestIndex = findSmallest(x);
    swap(x, start, smallestIndex);
    sort(x, start + 1);
}
```

However, the `findSmallest` method doesn't work well since it will start from 0 instead of the start point. We could easily fix it.

```java
public static int findSmallest(String[] x, int start) {
    int smallestIndex = start;
    for (int i = start; i < x.length; i += 1) {
        int cmp = x[i].compareTo(x[smallestIndex]);
        if (cmp < 0) {
            smallestIndex = i;
        }
    }
    return smallestIndex;
}
```

### Enhanced JUnit Test

To allow IntelliJ to automatically run the tests, we could modify our test structure as the following rules:

* Import the library `org.junit.Test`.
* Precede each method with `@Test` (no semi-colon).
* Change each test method to be non-static.
* Remove our main method from the `TestSort` class.
