public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> wordList= new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            wordList.addLast(word.charAt(i));
        }
        return wordList;
    }

    public boolean isPalindrome(String word) {
        if (word.length() == 0) {
            return true;
        } else {
            StringBuilder reverse = new StringBuilder();
            Deque<Character> wordList = wordToDeque(word);
            int size = wordList.size();
            for (int i = 0; i < size; i++) {
                reverse.append(wordList.removeLast());
            }
            return reverse.toString().equals(word);
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() == 0) {
            return true;
        } else {
            Deque<Character> wordList = wordToDeque(word);
            int size = wordList.size();
            for (int i = 0; i < size / 2; i++) {
                if (!cc.equalChars(wordList.get(i), wordList.get(size - 1 - i))) {
                    return false;
                }
            }
            return true;
        }
    }
}