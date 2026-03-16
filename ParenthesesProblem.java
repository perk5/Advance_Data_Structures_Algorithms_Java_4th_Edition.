public class ParenthesesProblem {
    public static boolean ParamMatch(String s) {
        StringBuilder newS = new StringBuilder("");
        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);

            if (newS.length() == 0 && (c == ')' || c == '}' || c == ']')) {
                return false;
            }

            if (c == '(' || c == '{' || c == '[') {
                newS.append(c);
                continue;
            }

            if (newS.length() >= 1) {
                if (c == ')' && newS.charAt(newS.length() - 1) == '(') {
                    newS.deleteCharAt(newS.length() - 1);
                } else if (c == '}' && newS.charAt(newS.length() - 1) == '{') {
                    newS.deleteCharAt(newS.length() - 1);
                } else if (c == ']' && newS.charAt(newS.length() - 1) == '[') {
                    newS.deleteCharAt(newS.length() - 1);
                } else {
                    return false;
                }
            }

        }
        return newS.length() == 0;
    }

    public static void main(String[] args) {
        String s = "()(){}";
        System.out.println(ParamMatch(s));
    }

}