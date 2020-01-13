import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
    private Node root;

    private static class Node {
        boolean isKey;
        HashMap<Character, Node> next;
        public Node(boolean isKey) {
            this.isKey = isKey;
            this.next = new HashMap<>();
        }
    }

    public MyTrieSet() {
        root = new Node(false);
    }

    @Override
    public void clear() {
        root = new Node(false);
    }

    public Character[] getCharList(String key) {
        Character[] ch = new Character[key.length()];
        for (int i = 0; i < key.length(); i++) {
            ch[i] = key.charAt(i);
        }
        return ch;
    }

    @Override
    public boolean contains(String key) {
        Node n = root;
        Character[] ch = getCharList(key);
        for (Character c : ch) {
            if (n.next.containsKey(c)) {
                n = n.next.get(c);
            } else {
                return false;
            }
        }
        return n.isKey;
    }

    @Override
    public void add(String key) {
        Node n = root;
        Character[] ch = getCharList(key);
        for (Character c : ch) {
            if (n.next.containsKey(c)) {
                n = n.next.get(c);
            } else {
                Node temp = new Node(false);
                n.next.put(c, temp);
                n = temp;
            }
        }
        n.isKey = true;
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        Node n = root;
        Character[] ch = getCharList(prefix);
        for (Character c : ch) {
            if (n.next.containsKey(c)) {
                n = n.next.get(c);
            } else {
                return null;
            }
        }
        List<String> list = new ArrayList<>();
        for (Character c : n.next.keySet()) {
            helperPrefix(list, n.next.get(c), prefix + c);
        }
        return list;
    }

    public void helperPrefix(List<String> list, Node n, String s) {
        if (n.isKey) {
            list.add(s);
        }
        for (Character c : n.next.keySet()) {
            helperPrefix(list, n.next.get(c), s + c);
        }
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
