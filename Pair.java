public class Pair<T, V> {
    private T first;
    private V second;

    public Pair(T t, V v) {
        first = t;
        second = v;
    }

    public T first() {
        return first;
    }

    public V second() {
        return second;
    }
}