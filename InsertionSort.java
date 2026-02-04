import java.util.*;

public class InsertionSort {

    public int[] sort(int[] arr){
        for(int i = 1; i < arr.length; i++){  
            for(int j = i; j > 0; j--){
                int temp = arr[j-1];
                if(arr[j] < arr[j - 1]){
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }

    public void main(String args[]){
        int[] arr = new int[]{5, 3, 8, 4, 2 };
        System.out.println(Arrays.toString(sort(arr)));
    }
}
