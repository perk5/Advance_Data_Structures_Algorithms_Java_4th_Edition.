public class ReversingCharString {

    public static String reverseString(char[] charArr, int l, int r) {
        if (l >= r) {
            String s = new String(charArr);
            return s;
        }

        char c = charArr[l];
        charArr[l] = charArr[r];
        charArr[r] = c;

        return reverseString(charArr, l + 1, r - 1);

    }

    public static void main(String args[]) {

        String str = "pots&pans";
        char[] s = str.toCharArray(); 
        System.out.println(reverseString(s, 0, s.length - 1));
    }
}
