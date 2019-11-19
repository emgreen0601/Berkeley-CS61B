# CS 61B Week 4

## Extends, Casting, Higher Order Functions

Although interfaces provide us a way to define a hierarchical relationship, the `extends` key word let us define a hierarchical relationship between classes.

If we want to define a new class which have all methods in the `SLList` but new ones such as `RotatingRight`.

```java
public class RotatingSLList<Item> extends SLList<Item>
```

With `extends` key word, the subclass withh inhert all these components:

* All instance and static variables
* All methods
* All nested classes

## VengefulSLList

We could build a `VengefulSLList` class to make a list that could remeber the deleted items. `super` key word could be used to call the corresponding method in the super class.

```java
public class VengefulSLList<Item> extends SLList<Item> {
    SLList<Item> deletedItems;

    public VengefulSLList() {
        deletedItems = new SLList<Item>();
    }

    @Override
    public Item removeLast() {
        Item x = super.removeLast();
        deletedItems.addLast(x);
        return x;
    }

    /** Prints deleted items. */
    public void printLostItems() {
        deletedItems.print();
    }
}
```

