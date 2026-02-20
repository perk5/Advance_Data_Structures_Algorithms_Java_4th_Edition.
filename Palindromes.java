public class Palindromes {

    public static boolean isPalindrome(String s) {
        if (s == null)
            return false;
        return helper(s, 0, s.length() - 1);
    }

    private static boolean helper(String s, int l, int r) {
        if (l >= r)
            return true;
        if (s.charAt(l) != s.charAt(r))
            return false;
        return helper(s, l + 1, r - 1);
    }

    public static void main(String args[]) {
        String str = "gohangasalamiimalasagnahog";
        System.out.println(isPalindrome(str));
    }
}
