public class RecursiveStrInt {

    public static int stringToInt(String str) {

        if (str.length() == 1) {
            return str.charAt(0) - '0';
        }

        int firstDigit = str.charAt(0) - '0';

        int restNumber = stringToInt(str.substring(1));

        return firstDigit * (int) Math.pow(10, str.length() - 1) + restNumber;

    }

    public static void main() {
        String s = "13531";
        int result = stringToInt(s);
        System.out.println(result);
    }
}
