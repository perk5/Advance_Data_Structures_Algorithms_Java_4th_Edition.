public class RecursiveBinarySum {

    public static int recurseSum(int[] a, int i, int n){

        if(n == 1){
            return a[i];
        }
        return recurseSum(a, i, n/2) + recurseSum(a, i + (n/2), n - n/2);

    }

    public static void main(String args[]){
        int[] arr = new int[]{1,2,3,4,5,6};

        System.out.println(recurseSum(arr, 0, arr.length));
    }
}
