public class PowerRecursive {

    // public static int recurse(int x, int n){

    //     if(n == 1){
    //         return x;
    //     }
    //     return x * recurse(x , n - 1);
        
    // }

    public static int recurse(int x, int n){

        if(n <= 0){
            return 1;
        }

        int y = recurse(x, n/2);
        if(n % 2 == 0){
            return y * y; 
        }else{
            return  x * y * y;
        }
    }

    public static void main(String args[]){
        System.out.println(recurse(2, 5));
    }
}
