import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AuthorDetector {

    public static void main(String args[]) throws IOException {

        String[] fileNames = { "AndrewCunningham.txt", "JohnTimmer.txt", "JonBrodkin.txt", "JenniferOuellette.txt", "KyleOrland.txt" };
        Fingerprint[] fingerprints = getFingerprints(fileNames);
        String[] testFileNames = new String[6];
        for (int i = 0; i < 6; i++) {
            testFileNames[i] = "Mystery" + (i + 1) + ".txt";
        }
        Fingerprint[] testFingerprints = getFingerprints(testFileNames);
        for (int i = 0; i < 6; i++) {
            Fingerprint testFingerprint = testFingerprints[i];
            System.out.println(testFileNames[i] + " results:");
            for (int j = 0; j < 5; j++) {
                Fingerprint authorFingerprint = fingerprints[j];
                String name = truncate(fileNames[j]);
                double[] result = authorFingerprint.similarity(testFingerprint);
                System.out.println(name);
                for (int k = 0; k < result.length; k++) {
                    System.out.println("Test " + (k + 1) + " - " + result[k] * 100);
                }
                System.out.println("Overall - " + overall(result) * 100);
                System.out.println("");
            }
        }
    }

    private static double overall(double[] result) {
        return result[0] + result[1] * 5 + result[2] * 50;
    }

    // private static void incrementFrequency(KHash table, String key) {
    //     Integer val = table.get(key);
    //     if (val == null) {
    //         val = 0;
    //     }
    //     table.put(key, val + 1);
    // }

    public static Fingerprint[] getFingerprints(String[] fileNames) throws IOException {
        Fingerprint[] ret = new Fingerprint[fileNames.length];
        for (int f = 0; f < fileNames.length; f++) {
            ret[f] = new Fingerprint(readWords(fileNames[f]));
        }
        return ret;
    }

    private static String truncate(String input) {
        int index = input.indexOf('.');
        return input.substring(0, index);
    }

    // private static KHash[] getFingerprint(String filename) throws IOException {
    //     KHash singleHash = new KHash();
    //     KHash doubleHash = new KHash();
    //     KHash tripleHash = new KHash();

    //     ArrayList<String> words = (ArrayList<String>) readWords(filename);
    //     for (int i = 0; i < words.size(); i++) {
    //         if (!words.get(i).equals("the")) incrementFrequency(singleHash, words.get(i));
    //         int wordsLeft = words.size() - i;
    //         if (wordsLeft > 2) {
    //             String phrase = words.get(i) + " " + words.get(i + 1) + " " + words.get(i + 2);
    //             incrementFrequency(tripleHash, phrase);
    //         } else if (wordsLeft > 1) {
    //             String phrase = words.get(i) + " " + words.get(i + 1);
    //             incrementFrequency(doubleHash, phrase);
    //         }
    //     }
    //     return new KHash[] { singleHash, doubleHash, tripleHash };
    // }

    @SuppressWarnings("ConvertToTryWithResources")
    private static List<String> readWords(String filename) throws IOException {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            result.addAll(Arrays.asList(scanner.nextLine().toLowerCase().replaceAll("[^a-z ]", "").split("\\s+")));
        }
        scanner.close();
        return result;
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public static List<String> readPunctuation(String filename) throws IOException {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            result.addAll(Arrays.asList(scanner.nextLine().toLowerCase().replaceAll("[^a-z ]", "").split("\\s+")));
        }
        scanner.close();
        return result;
    }
    /*
    class Fingerprint {
        singleHash
        doubleHash
        tripleHash    

        fillHashes()
        compareTo(Fingerprint other)
    }
    */
}