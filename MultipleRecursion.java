import java.util.*;

public class MultipleRecursion {
    public void generate(List<Character> chosen, List<Character> remaining) {

        // STOP condition
        if (remaining.isEmpty()) {
            System.out.println(chosen);
            return;
        }

        // MULTIPLE choices
        for (char c : remaining) {

            // make a choice
            chosen.add(c);

            // remove chosen letter
            List<Character> newRemaining = new ArrayList<>(remaining);
            newRemaining.remove((Character) c);

            // RECURSION (this is the key)
            generate(chosen, newRemaining);

            // undo choice
            chosen.remove(chosen.size() - 1);
        }
    }

    public void main(String args[]){
        List<Character> chosen = new ArrayList<>();
        List<Character> remaining = new ArrayList<>();
        remaining.add('a');
        remaining.add('b');
        remaining.add('c');
        generate(chosen, remaining);
    }
}
