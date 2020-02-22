package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<PriorityNode> items;
    private HashMap<T, Integer> keys;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        keys = new HashMap<>();
        items.add(null);
    }

    private void swim(int k) {
        if (k != 1 && items.get(k).compareTo(items.get(k / 2)) < 0) {
            swap(k, k / 2);
            swim(k / 2);
            keys.put(items.get(k).getItem(), k / 2);
            keys.put(items.get(k / 2).getItem(), k);
        }
    }

    private void sink(int k) {
        if (k * 2 <= size() && items.get(k).compareTo(items.get(k * 2)) > 0) {
            swap(k, k * 2);
            sink(k * 2);
            keys.put(items.get(k).getItem(), k * 2);
            keys.put(items.get(k * 2).getItem(), k);
        } else if (k * 2 + 1 <= size() && items.get(k).compareTo(items.get(k * 2 + 1)) > 0) {
            swap(k, k * 2 + 1);
            sink(k * 2 + 1);
            keys.put(items.get(k).getItem(), k * 2 + 1);
            keys.put(items.get(k * 2 + 1).getItem(), k);
        }
    }

    private void swap(int k, int j) {
        PriorityNode temp = items.get(k);
        items.set(k, items.get(j));
        items.set(j, temp);
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        PriorityNode node = new PriorityNode(item, priority);
        items.add(node);
        keys.put(item, size());
        swim(size());
    }

    @Override
    public boolean contains(T item) {
        return keys.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size() == 1) {
            throw new NoSuchElementException();
        }
        return items.get(1).getItem();
    }

    @Override
    public T removeSmallest() {
        if (size() == 1) {
            throw new NoSuchElementException();
        }
        T temp = getSmallest();
        swap(1, size());
        items.remove(size());
        sink(size());
        keys.remove(temp);
        return temp;
    }

    @Override
    public int size() {
        return items.size() - 1;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (size() == 1 || !contains(item)) {
            throw new NoSuchElementException();
        }
        int index = keys.remove(item);
        items.remove(index);
        add(item, priority);
    }

    private class PriorityNode {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
}
