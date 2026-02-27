import java.util.*;

public class EvenOdd {

    public static int[] EvenOdd(int[] arr, int l, int r) {

        if (l >= r) {
            return arr;
        }

        if (arr[l] % 2 == 0) {
            return EvenOdd(arr, l + 1, r);
        } else if (arr[l] % 2 == 1 && arr[r] % 2 == 0) {
            int temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            return EvenOdd(arr, l + 1, r - 1);
        } else if (arr[l] % 2 == 1 && arr[r] % 2 == 1) {
            return EvenOdd(arr, l, r - 1);
        }

        return EvenOdd(arr, l + 1, r - 1);

    }

    public static void main(String args[]) {

        int[] arr = new int[] { 3, 8, 9, 6, 5, 2 };

        System.out.println(Arrays.toString(EvenOdd(arr, 0, arr.length - 1)));
    }
}
