public class VowelsVsConsonents {
    public static boolean vowelsConsonents(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        String s = str.toLowerCase();
        return helper(s, 0, 0);

    }

    private static boolean helper(String str, int index, int count) {

        if (index >= str.length()) {
            return count > 0;
        }

        char ch = str.charAt(index);

        if (Character.isLetter(ch)) {
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                count++;
            } else {
                count--;
            }
        }
        return helper(str, index + 1, count);
    }

    public static void main(String args[]) {
        String str = "AbAb";
        System.out.println(vowelsConsonents(str));
    }
}
