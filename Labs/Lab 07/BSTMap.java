import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;
    private int size;
    private class Node {
        private K key;           // sorted by key
        private V val;         // associated data
        private Node left, right;  // left and right subtrees

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    public BSTMap() {
        size = 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        if (root == null) {
            return null;
        }
        return get(key, root);
    }

    private V get(K key, Node n) {
        if (key == null) {
            throw new IllegalArgumentException();
        } else if (n == null) {
            return null;
        }
        int c = key.compareTo(n.key);
        if (c == 0) {
            return n.val;
        } else if (c>0) {
            return get(key, n.right);
        } else {
            return get(key, n.left);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        root = put(key, value, root);
    }

    private Node put(K key, V value, Node n) {
        if (key == null) {
            throw new IllegalArgumentException();
        } else if (n == null) {
            size ++;
            return new Node(key, value);
        }

        int c = key.compareTo(n.key);
        if (c == 0) {
            n.val = value;
        } else if (c>0) {
            n.right = put(key, value, n.right);
        } else {
            n.left = put(key, value, n.left);
        }
        return n;
    }

    @Override
    public Set keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node x) {
        if (x != null)
        {
            printInOrder(x.left);
            System.out.print(x.key + " ");
            printInOrder(x.right);
        }
    }
}
