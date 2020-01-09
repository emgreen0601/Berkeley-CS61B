import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {
    Set<K> keys = new HashSet<>();
    ArrayList<Bucket> buckets;
    int maxSize;
    int n;
    double loadFactor;

    class Bucket {
        ArrayList<Item> itemsList;

        public Bucket() {
            itemsList = new ArrayList<>();
        }

        public void add(K key, V val) {
            itemsList.add(new Item(key, val));
        }

        public V remove(K key) {
            for (Item i : itemsList) {
                if (i.key == key) {
                    itemsList.remove(i);
                    return i.val;
                }
            }
            return null;
        }

        public V remove(K key, V val) {
            for (Item i : itemsList) {
                if (i.key == key && i.val == val) {
                    itemsList.remove(i);
                    return val;
                }
            }
            return null;
        }

        public V get(K key) {
            for (Item i : itemsList) {
                if (i.key == key) {
                    return i.val;
                }
            }
            return null;
        }

        class Item {
            K key;
            V val;

            public Item(K key, V val) {
                this.key = key;
                this.val = val;
            }
        }
    }

    public MyHashMap() {
        this(16);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        maxSize = initialSize;
        this.loadFactor = loadFactor;
        clear();
    }

    @Override
    public void clear() {
        buckets = initBuckets();
    }

    private ArrayList<Bucket> initBuckets() {
        ArrayList<Bucket> bucketsList = new ArrayList<>();
        for (int i = 0; i < maxSize; i++) {
            bucketsList.add(new Bucket());
        }
        return bucketsList;
    }

    @Override
    public boolean containsKey(K key) {
        return keys.contains(key);
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        } else if (keys.contains(key)) {
            int index = (key.hashCode() & 0x7fffffff) % maxSize;
            Bucket b = buckets.get(index);
            return b.get(key);
        }
        return null;
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (containsKey(key)) {
            remove(key);
        }
        int index = (key.hashCode() & 0x7fffffff) % maxSize;
        Bucket b = buckets.get(index);
        b.add(key, value);
        keys.add(key);
        n += 1;
        if ((double) n / maxSize >= loadFactor) {
            resize(maxSize * 2);
        }
    }

    private void resize(int size) {
        ArrayList<Bucket> temp = initBuckets();
        for (int i = 0; i < size; i++) {
            temp.add(new Bucket());
        }
        for (K key : keys) {
            int index = (key.hashCode() & 0x7fffffff) % size;
            Bucket b = temp.get(index);
            b.add(key, get(key));
        }
        buckets = temp;
        maxSize = size;
    }

    @Override
    public Set<K> keySet() {
        return keys;
    }

    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        keys.remove(key);
        n -= 1;
        int index = (key.hashCode() & 0x7fffffff) % maxSize;
        Bucket b = buckets.get(index);
        return b.remove(key);
    }

    @Override
    public V remove(K key, V value) {
        if (!containsKey(key)) {
            return null;
        }
        keys.remove(key);
        n -= 1;
        int index = (key.hashCode() & 0x7fffffff) % maxSize;
        Bucket b = buckets.get(index);
        return b.remove(key, value);
    }

    @Override
    public Iterator<K> iterator() {
        return keys.iterator();
    }
}
