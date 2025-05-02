import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class WordCounter {

    public static void main(String[] args) {
        String fileName = "A text-book of veterinary anatomy.txt";
        List<String> wordList = readWordsFromFile(fileName);

        if (wordList == null) {
            System.out.println("Error reading from file.");
            return;
        }

        // TreeMap timing and processing
        long treeStart = System.nanoTime();
        Map<String, Integer> treeMap = countWords(new TreeMap<>(), wordList);
        long treeEnd = System.nanoTime();
        System.out.println("Top 5 words in TreeMap:");
        printTopWords(treeMap);
        System.out.printf("TreeMap time: %.2f ms%n", (treeEnd - treeStart) / 1_000_000.0);

        // HashMap timing and processing
        long hashStart = System.nanoTime();
        Map<String, Integer> hashMap = countWords(new HashMap<>(), wordList);
        long hashEnd = System.nanoTime();
        System.out.println("\nTop 5 words in HashMap:");
        printTopWords(hashMap);
        System.out.printf("HashMap time: %.2f ms%n", (hashEnd - hashStart) / 1_000_000.0);
    }

    // Reads file, removes punctuation, splits into words, stores in list
    private static List<String> readWordsFromFile(String fileName) {
        List<String> words = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNext()) {
                String word = scanner.next()
                        .replaceAll("[,\\.\\?!]", "")  // remove specified punctuation
                        .toLowerCase();
                if (!word.isEmpty()) {
                    words.add(word);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            return null;
        }
        return words;
    }

    // Count word occurrences in provided map
    private static Map<String, Integer> countWords(Map<String, Integer> map, List<String> words) {
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        return map;
    }

    // Print top 5 most frequent words longer than 6 characters
    private static void printTopWords(Map<String, Integer> map) {
        map.entrySet().stream()
                .filter(entry -> entry.getKey().length() > 6)
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5)
                .forEach(entry ->
                        System.out.println(entry.getKey() + ": " + entry.getValue()));
    }
}
