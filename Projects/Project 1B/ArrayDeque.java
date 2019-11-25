import java.lang.reflect.Array;

class ArrayDeque<T> implements Deque<T>{
    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] items;

    public int addOne(int i) {
        return (i + 1) % items.length;
    }

    public int minusOne(int i) {
        return (i - 1 + items.length) % items.length;
    }

    public boolean isFull() {
        return size == items.length;
    }

    public boolean isSparse() {
        return items.length >= 16 && size < (items.length / 4);
    }

    public void resize(int length) {
        T[] cache = (T[]) new Object[length];
        int oldIndex = addOne(nextFirst);
        for (int i = 0; i < size; i++) {
            cache[i] = items[oldIndex];
            oldIndex = addOne(oldIndex);
        }
        items = cache;
        nextFirst = length - 1;
        nextLast = size;
    }

    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    public ArrayDeque(ArrayDeque other) {
        items = (T[]) new Object[other.items.length];
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
        size = other.size;
        System.arraycopy(other.items, 0, items, 0, other.items.length);
    }

    @Override
    public void addFirst(T item) {
        if (isFull()) {
            resize(items.length * 2);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (isFull()) {
            resize(items.length * 2);
        }
        items[nextLast] = item;
        nextLast = addOne(nextLast);
        size -= 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int index = addOne(nextFirst);
        for (int i = 0; i < size; i++) {
            System.out.print(items[index] + " ");
            index = addOne(index);
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isSparse()) {
            resize(items.length / 2);
        }
        nextFirst = addOne(nextFirst);
        T item = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;
        if (!isEmpty()) {
            size -= 1;
        }
        return item;
    }

    @Override
    public T removeLast() {
        if (isSparse()) {
            resize(items.length / 2);
        }
        nextLast = minusOne(nextLast);
        T item = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        if (!isEmpty()) {
            size -= 1;
        }
        return item;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int start = addOne(nextFirst);
        return items[(start + index) % items.length];
    }
}