import java.util.*;

public class HtmlTags {

    public static boolean matchHtmlTags(String[] htmlTags) {

        Stack<String> stack = new Stack<>();

        for (String s : htmlTags) {
            if (!s.startsWith("/")) {
                stack.push(s);
            } else {

                if (stack.isEmpty()) {
                    return false;
                }

                String top = stack.pop();

                if (!s.substring(1).equals(top)) {
                    return false;
                }

            }

        }
        return stack.isEmpty();

    }

    public static void main(String args[]) {
        String[] elements = new String[] { "body", "p", "/p", "/body" };
        System.out.println(matchHtmlTags(elements));
    }

}
