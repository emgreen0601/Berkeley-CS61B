public class OffByN implements CharacterComparator {
    private int setOff;

    public OffByN(int N) {
        setOff = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return y - x == setOff;
    }
}
