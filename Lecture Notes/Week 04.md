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

### Constructors

While constructors are not inherited, Java requires that all constructors must start with a call to one of its superclass's constructors. If you don't call it explicitly, Java will automatically call it for you. If we forget to specify which contructor to use, Java will call the default one without parameters.

### The Object Class

Every class in Java is a descendant of the Object class, or extends the Object class. Even classes that do not have an explicit extends in their class still implicitly extend the Object class.

### Encapsulation

* A model is a set of methods working together to perform some task or set of related tasks.
* A model is said to be encapsulated if its implementation is highly hidden: It can be accessed merely through the documented interfaces.

### Type Checking and Casting

Compilers will check types of objects based on its staitc type. For instance, the following code will result in a compile-time error since the compiler thinks that `SLList` does not have the `printLostItem` method and `vsl2` can't contain the `SLList` object.

```java
VengefulSLList<Integer> vsl = new VengefulSLList<Integer>(9);
SLList<Integer> sl = vsl;
sl.printLostItems();
VengefulSLList<Integer> vsl2 = sl;
```

#### Expressions

As we seen above, expression with `new` key word has compile-time types.

```java
SLList<Integer> sl = new VengefulSLList<Integer>();
```

Above, the compile-time type of the right-hand side of the expression is `VengefulSLList`. The compiler checks to make sure that `VengefulSLList` "is-a" `SLList`, and allows this assignment.

#### Method

The type of a method's return value is the method's compile-time type. Since the return type of `maxDog` is `Dog`, any call to `maxDog` will have compile-time type `Dog`.

```java
Poodle frank = new Poodle("Frank", 5);
Poodle frankJr = new Poodle("Frank Jr.", 15);

Dog largerDog = maxDog(frank, frankJr);
Poodle largerPoodle = maxDog(frank, frankJr); //does not compile! RHS has compile-time type Dog
```

#### Casting

We could specify the type of an expression or a method to let Java compiler ignore type check. That might be dangerous and may cause run-time errors.

```java
Poodle largerPoodle = (Poodle) maxDog(frank, frankJr); // compiles! Right hand side has compile-time type Poodle after casting
```


### High Order Functions

In Python, we could define a function that will take another function as a parameter.

```python
def tenX(x):
    return 10 * x

def do_twice(f, x):
    return f(f(x))
```

In Java, we could do so by declaring an interface.

```java
public interface IntUnaryFunction{
    int apply(int x) {}
}

public class TenX implements IntUnaryFunction{
    public int apply(int x) {
        return 10 * x;
    }

    
}
```

```java
public static int do_twice(IntUnaryFunction f, int x) {
    return f.apply(f.apply(x));
}

System.out.println(do_twice(new TenX(), 2));
```

## Subtype Polymorphism vs. HoFs

### Max Function

Suppose we will write a method `max` that will take an array of objects and return the maximum one.

```java
public static Object max(Object[] items) {
    int maxDex = 0;
    for (int i = 0; i < items.length; i += 1) {
        if (items[i] > items[maxDex]) {
            maxDex = i;
        }
    }
    return items[maxDex];
}

public static void main(String[] args) {
    Dog[] dogs = {new Dog("Elyse", 3), new Dog("Sture", 9), new Dog("Benjamin", 15)};
    Dog maxDog = (Dog) max(dogs);
    maxDog.bark();
}
```

The problem is that the `Dog` object can't work with the `>` operater.
To fix this problem, we may change the function slightly.

```java
public static Dog maxDog(Dog[] dogs) {
    if (dogs == null || dogs.length == 0) {
        return null;
    }
    Dog maxDog = dogs[0];
    for (Dog d : dogs) {
        if (d.size > maxDog.size) {
            maxDog = d;
        }
    }
    return maxDog;
}
```

However, another problem is that we couldn't generalize this function to other type of objects.

### compareTo

We can create an interface that guarantees that any implementing class, like `Dog`, contains a comparison method, which we'll call `compareTo`.

Let's write our own interface.

```java
public interface OurComparable {
    public int compareTo(Object o);
}
```

* Return a negative number if `this` < o.
* Return 0 if `this` equals o.
* Return a positive number if `this` > o.

Now, we could let our `Dog` class implements the `OurComparable` interface.

```java
public class Dog implements OurComparable {
    private String name;
    private int size;

    public Dog(String n, int s) {
        name = n;
        size = s;
    }

    public void bark() {
        System.out.println(name + " says: bark");
    }

    public int compareTo(Object o) {
        Dog uddaDog = (Dog) o;
        return this.size - uddaDog.size;
    }
}
```

Then, we could generalize the `max` function by taking in `OurComparable`  objects.

```java
public static OurComparable max(OurComparable[] items) {
    int maxDex = 0;
    for (int i = 0; i < items.length; i += 1) {
        int cmp = items[i].compareTo(items[maxDex]);
        if (cmp > 0) {
            maxDex = i;
        }
    }
    return items[maxDex];
}
```

### Comparables

Although `OurComparable` interface seems solved the issue, it's awkward to use and there's no existing classes implement `OurComparable`.
The solution is that we could use the existed interface `Comparable`.

```java
public interface Comparable<T> {
    public int compareTo(T obj);
}
```

Notice that `Comparable<T>` means that it takes a generic type. This will help us avoid having to cast an object to a specific type.

```java
public class Dog implements Comparable<Dog> {
    ...
    public int compareTo(Dog uddaDog) {
        return this.size - uddaDog.size;
    }
}
```

### Comparator

We could only implement one `compareTo` method for each class. However, if we want to add more orders of comparasion, we could implement `Comparator` interface.

```java
public interface Comparator<T> {
    int compare(T o1, T o2);
}
```
This shows that the `Comparator` interface requires that any implementing class implements the `compare` method.

To compare two dogs based on their names, we can simply defer to `String`'s already defined `compareTo` method.

```java
import java.util.Comparator;

public class Dog implements Comparable<Dog> {
    ...
    public int compareTo(Dog uddaDog) {
        return this.size - uddaDog.size;
    }

    private static class NameComparator implements Comparator<Dog> {
        public int compare(Dog a, Dog b) {
            return a.name.compareTo(b.name);
        }
    }

    public static Comparator<Dog> getNameComparator() {
        return new NameComparator();
    }
}
```

```java
Comparator<Dog> nc = Dog.getNameComparator();
```