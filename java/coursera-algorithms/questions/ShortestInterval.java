import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Coursera | Algorithms, Part I | Week 05
 * Design an algorithm that takes a sequence of n document words and a sequence of m query words and find the shortest
 * interval in which the m query words appear in the document in the order given. The length of an interval is
 * the number of words in that interval.
 */
public class ShortestInterval {
    public static int[] findShortestInterval(String[] document, String[] query) {
        // BST to store the indices of each word in the document
        Map<String, List<Integer>> documentIndices = new TreeMap<>();

        // Preprocess the document to store the indices of each word
        for (int i = 0; i < document.length; i++) {
            documentIndices.computeIfAbsent(document[i], k -> new ArrayList<>()).add(i);
        }

        int minLength = Integer.MAX_VALUE;
        int[] result = new int[]{-1, -1};

        // Get the list of all indices for the first query word
        List<Integer> firstIndices = documentIndices.get(query[0]);
        if (firstIndices == null) {
            return result; // [-1, -1]
        }

        for (int start : firstIndices) {
            int currentIndex = start;
            boolean found = true;

            for (int i = 1; i < query.length; i++) {
                String currentQueryWord = query[i];
                List<Integer> currentWordIndices = documentIndices.get(currentQueryWord);
                if (currentWordIndices == null) {
                    found = false;
                    break;
                }

                // Find the smallest index greater than currentIndex
                int nextIndex = binarySearch(currentWordIndices, currentIndex);
                if (nextIndex == -1) {
                    found = false;
                    break;
                }

                currentIndex = currentWordIndices.get(nextIndex);
            }

            if (found) {
                int intervalLength = currentIndex - start + 1;
                if (intervalLength < minLength) {
                    minLength = intervalLength;
                    result[0] = start;
                    result[1] = currentIndex;
                }
            }
        }

        return result;
    }

    private static int binarySearch(List<Integer> indices, int target) {
        int left = 0;
        int right = indices.size() - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (indices.get(mid) > target) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        String[] document = {
                "Lorem", "ipsum", "dolor", "sit", "amet",
                "consectetur", "adipiscing", "elit", "sed", "do",
                "eiusmod", "tempor", "incididunt", "ut", "labore",
                "et", "dolore", "magna", "aliqua", "Ut",
                "ipsum", "consectetur", "adipiscing", "elit", "ut"
        };
        String[] query = {"ipsum", "elit", "ut"};

        int[] interval = findShortestInterval(document, query);
        System.out.println("Shortest interval: [" + interval[0] + ", " + interval[1] + "]");
    }
}
