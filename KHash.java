import java.util.*;

public class KHash {
    private int capacity = 16;
    private int size = capacity;
    @SuppressWarnings("unchecked")
    private List<Pair<String, Integer>>[] array = (List<Pair<String, Integer>>[]) new List[capacity];

    private int hash(String str) {
        int total = 0;
        for (int i = 0; i < str.length(); i++) {
            total += str.charAt(i);
        }
        return total % capacity;
    }

    public void put(String key, Integer value) {
        int hash = hash(key);
        if (array[hash] == null)
            array[hash] = new ArrayList<Pair<String, Integer>>();
        for (int i = 0; i < array[hash].size(); i++) {
            Pair<String, Integer> term = array[hash].get(i);
            if (key.equals(term.first())) {
                array[hash].set(i, new Pair<String, Integer>(key, value));
                return;
            }
        }
        array[hash].add(new Pair<String, Integer>(key, value));
        size++;
        if (size * 3 / 4 >= capacity)
            grow();
    }

    public Integer get(String key) {
        int hash = hash(key);
        if (array[hash] == null)
            return null;
        for (int i = 0; i < array[hash].size(); i++) {
            Pair<String, Integer> term = array[hash].get(i);
            if (key.equals(term.first())) {
                return term.second();
            }
        }
        return null;
    }

    public String remove(String key) {
        int hash = hash(key);
        if (array[hash] == null)
            return null;
        for (int i = 0; i < array[hash].size(); i++) {
            Pair<String, Integer> term = array[hash].get(i);
            if (key.equals(term.first())) {
                return array[hash].remove(i).first();
            }
        }
        return null;
    }

    private void grow() {
        capacity *= 2;
        List<Pair<String, Integer>>[] arrOld = array;
        @SuppressWarnings("unchecked")
        List<Pair<String, Integer>>[] arrNew = (List<Pair<String, Integer>>[]) new List[capacity];
        array = arrNew;

        size = 0;
        for (int i = 0; i < arrOld.length; i++) {
            if (arrOld[i] == null)
                continue;
            for (Pair<String, Integer> pair : arrOld[i]) {
                put(pair.first(), pair.second());
            }
        }
    }

    public List<String> keys() {
        List<String> res = new ArrayList<>();
        for (var list : array) {
            if (list == null) continue;
            for (Pair<String, Integer> pair : list) {
                res.add(pair.first());
            }
        }
        return res;
    }

    public String toString() {
        String res = "{ ";
        boolean first = true;
        for (String key : keys()) {
            if (first) {
                first = false;
            } 
            else res += ", ";
            res += key + ": " + get(key);
        }
        res += " }";
        return res;
    }
}
