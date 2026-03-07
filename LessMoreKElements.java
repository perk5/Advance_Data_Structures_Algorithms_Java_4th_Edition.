import java.util.*;

public class LessMoreKElements {

    public static int[] sortLesserThanK(int[] arr, int left, int right, int k) {

        if (left >= right) {
            return arr;
        }
 
        if (arr[left] <= k) {  
            return sortLesserThanK(arr, left + 1, right, k);
        }else if(arr[left] > k && arr[right] > k){
            return sortLesserThanK(arr, left, right - 1, k);
        }else if(arr[left] > k && arr[right] <= k){
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            return sortLesserThanK(arr, left + 1, right - 1, k);
        }

        return arr;

    }

    public static void main() {
        int[] arr = new int[] {5, 8, 4, 3, 2, 1, 9, 10 };
        int k = 6;
        System.out.println(Arrays.toString(sortLesserThanK(arr, 0, arr.length - 1, k)));
    }
}