import java.util.*;

public class SubsetsUsingStackQueue {

    public static void generateSubsets(char[] T) {
        Stack<List<Character>> S = new Stack<>();
        Queue<List<Character>> Q = new LinkedList<>();

        int n = T.length;

        // Start with empty subset
        S.push(new ArrayList<>());

        while (!S.isEmpty()) {
            List<Character> current = S.pop();

            // Add current subset to queue (store result)
            Q.add(current);

            // Find next element to add
            int lastIndex = -1;
            if (!current.isEmpty()) {
                char lastElement = current.get(current.size() - 1);
                for (int i = 0; i < n; i++) {
                    if (T[i] == lastElement) {
                        lastIndex = i;
                        break;
                    }
                }
            }

            // Generate new subsets by adding remaining elements
            for (int i = lastIndex + 1; i < n; i++) {
                List<Character> newSubset = new ArrayList<>(current);
                newSubset.add(T[i]);
                S.push(newSubset);
            }
        }

        // Print all subsets stored in queue
        while (!Q.isEmpty()) {
            System.out.println(Q.poll());
        }
    }

    public static void main(String[] args) {
        char[] T = {'a', 'b', 'c'};
        generateSubsets(T);
    }
}