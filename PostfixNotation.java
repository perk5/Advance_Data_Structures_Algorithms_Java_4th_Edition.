import java.util.*;

public class PostfixNotation {

    public static double answer(String arr) {
        Stack<Double> s = new Stack<>();

        for (char c : arr.toCharArray()) {

            if (c == ' ')
                continue;

            if (Character.isDigit(c)) {
                s.push((double) (c - '0'));
            } else {
                Double first = s.pop();
                Double second = s.pop();

                if (c == '+') {
                    s.push(second + first);
                } else if (c == '-') {
                    s.push(second - first);
                } else if (c == '*') {
                    s.push(second * first);
                } else if (c == '/') {
                    s.push(second / first);
                }
            }

        }
        return s.pop();
    }

    public static void main(String args[]) {
        String s = "5 2 + 8 3 - * 4 /";

        System.out.println(answer(s));
    }
}
