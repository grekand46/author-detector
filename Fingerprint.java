import java.util.List;
import java.util.Set;

public class Fingerprint {
    private static Set<String> ignoredWords = Set.of("the", "", "this", "a");

    private KHash singleHash = new KHash();
    private KHash doubleHash = new KHash();
    private KHash tripleHash = new KHash();

    private static void incrementFrequency(KHash table, String key) {
        Integer val = table.get(key);
        if (val == null) {
            val = 0;
        }
        table.put(key, val + 1);
    }

    public Fingerprint(List<String> words) {
        for (int i = 0; i < words.size(); i++) {
            if (!ignoredWords.contains(words.get(i)))
                incrementFrequency(singleHash, words.get(i));
            int wordsLeft = words.size() - i;
            if (wordsLeft > 2) {
                String phrase = words.get(i) + " " + words.get(i + 1) + " " + words.get(i + 2);
                incrementFrequency(tripleHash, phrase);
            } 
            if (wordsLeft > 1) {
                String phrase = words.get(i) + " " + words.get(i + 1);
                incrementFrequency(doubleHash, phrase);
            }
        }
    }

    private static double kHashScore(KHash self, KHash sample) {
        double size = wordCount(self);
        double sampleSize = wordCount(sample);
        double score = 0;
        int testCount = 0;
        for (String word : self.keys()) {
            Integer val = sample.get(word);
            if (val != null) {
                double diff = val / sampleSize - self.get(word) / size;
                score += 1 - diff * diff / wordWeight(word);
            }
            testCount++;
        }
        return score / testCount;
    }

    public double[] similarity(Fingerprint sample) {
        double scoreSingle = kHashScore(singleHash, sample.singleHash);
        double scoreDouble = kHashScore(doubleHash, sample.doubleHash);
        double scoreTriple = kHashScore(tripleHash, sample.tripleHash);
        return new double[] { scoreSingle, scoreDouble, scoreTriple };
    }

    private static int wordCount(KHash hash) {
        int res = 0;
        for (String key : hash.keys()) {
            res += hash.get(key);
        }
        return res;
    }

    private static double wordWeight(String word) {
        double res = 0.0;
        for (int i = 0; i < word.length(); i++) {
            res += 0.5 * (word.charAt(i) > 127 ? 1 : 5);
        }
        return res;
    }
}
